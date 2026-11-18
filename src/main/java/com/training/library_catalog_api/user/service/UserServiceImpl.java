package com.training.library_catalog_api.user.service;


import com.training.library_catalog_api.user.dto.LoginRequestDTO;
import com.training.library_catalog_api.user.dto.RegistrationRequestDTO;
import com.training.library_catalog_api.user.dto.TokenResponseDTO;
import com.training.library_catalog_api.user.dto.UserResponseDTO;
import com.training.library_catalog_api.user.entity.Role;
import com.training.library_catalog_api.user.entity.User;
import com.training.library_catalog_api.user.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLOutput;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    //   private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Override
    public UserResponseDTO register(RegistrationRequestDTO registerRequest) {
        System.out.println("Registration service reached");
        String email = registerRequest.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            System.out.println("Duplicate email detected");
            throw new ResponseStatusException(

                    HttpStatus.CONFLICT,
                    "Email is already registered"
            );

        }


        String passwordHash = encoder.encode(registerRequest.password());

        User user = new User();
        user.setName(registerRequest.name().trim());
        user.setEmail(email);
//        user.setPasswordHash(encoder.encode(registerRequest.password()));
//        user.setPasswordHash(encoder.encode(user.getPassword()));
        user.setPasswordHash(passwordHash);
        user.setRole(Role.MEMBER);

        userRepository.save(user);
        return UserResponseDTO.from(user);
    }

    @Override
    public TokenResponseDTO login(LoginRequestDTO loginRequest) {
        System.out.println("Login service reached");
        String email = loginRequest.email().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"
                ));
        System.out.println("user found" +user.getEmail());

        //verifying the password
        boolean passwordMatcher = encoder.matches(
                loginRequest.password(),
                user.getPasswordHash()
        );
        System.out.println("Password matched:" + passwordMatcher);
        //match should be used
        if (!passwordMatcher) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    " Invalid email or password "
            );
        }
String accessToken=jwtService.generationToken(
        user.getId(),
        user.getRole().name()
);
        return new TokenResponseDTO(
                accessToken,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }

    @Override
    public UserResponseDTO authMe(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        return UserResponseDTO.from(user);
    }
}

