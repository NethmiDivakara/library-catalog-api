package com.training.library_catalog_api.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.training.library_catalog_api.user.entity.Role;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {


    public static final String ROLE= "role";

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecretKey jwtSecretKey() {
        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("jwt.secret.key must be set");
        }

        byte [] decodedKey;
        try {
            decodedKey = Base64.getDecoder().decode(secretKey.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("jwt.secret.key must be valid Base64", e);
        }

        if (decodedKey.length < 32) {
            throw new IllegalStateException("jwt.secret.key must decode to at least 32 bytes for HS256");
        }

        return new SecretKeySpec(decodedKey, "HmacSHA256");
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKey jwtSecretKey) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecretKey)); //encoding the secret key to make the signature
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey jwtSecretKey,
                                 @Value("${jwt.issuer}") String issuer) {

        if (issuer == null || issuer.isBlank()) {
            throw new IllegalStateException("jwt.issuer must not be blank");
        }


        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withSecretKey(jwtSecretKey)
                .macAlgorithm(MacAlgorithm.HS256) //symmetric algorithm
                .build();


        OAuth2TokenValidator<Jwt> requireExpiry =
                new JwtClaimValidator<Instant>(JwtClaimNames.EXP, Objects::nonNull);

        OAuth2TokenValidator<Jwt> requireSubject =
                new JwtClaimValidator<String>(JwtClaimNames.SUB, s -> s != null && !s.isBlank());

        Set<String> allowedRoles = Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.toSet());

        OAuth2TokenValidator<Jwt> requireKnownRole =
                new JwtClaimValidator<String>(ROLE, r -> r != null && allowedRoles.contains(r));


        decoder.setJwtValidator(JwtValidators.createDefaultWithValidators(
                new JwtIssuerValidator(issuer),
                requireExpiry,
                requireSubject,
                requireKnownRole));

        return decoder;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authorities = new JwtGrantedAuthoritiesConverter();
        authorities.setAuthoritiesClaimName(ROLE);
        authorities.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authorities);
        return converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationConverter jwtAuthenticationConverter)
            throws Exception {

        final String MEMBER=Role.MEMBER.name();
        final String ADMIN =Role.ADMIN.name();

        http
                .csrf(csrf -> csrf.disable())   // token travels in a header the client adds, not in a cookie
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register", "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/me").hasAnyRole(MEMBER, ADMIN)

                        .requestMatchers(HttpMethod.GET, "/api/v1/books", "/api/v1/books/**").hasAnyRole(MEMBER, ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v1/books","/api/v1/books/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/books/**","/api/v1/books/**").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/books","/api/v1/books/**").hasRole(ADMIN)

                        .anyRequest().authenticated()
                );

        return http.build();
    }
}