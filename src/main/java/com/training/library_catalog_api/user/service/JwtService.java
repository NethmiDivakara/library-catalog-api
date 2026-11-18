package com.training.library_catalog_api.user.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration-seconds}")
    private long expirySeconds;

    @PostConstruct
    void validateConfig(){ //validation means we are checking the format and structure
        if(issuer == null || issuer.isBlank()){
            throw new IllegalStateException("Issuer must not be blank");
        }
        if (expirySeconds <=0){
            throw new IllegalStateException ("Expiration seconds must be greater than 0");
        }
    }

    public String generationToken(Long userId, String role) {

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder() //body of the json web token(payload)
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirySeconds))
                .subject(userId.toString())
                .claim("role", role)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256)//header with algorithm
                .build();

        return jwtEncoder.encode (
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }

    public long getExpirationSeconds() {
        return expirySeconds;
    }
}