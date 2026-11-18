package com.training.library_catalog_api.user.adminAcc;

import com.training.library_catalog_api.user.entity.Role;
import com.training.library_catalog_api.user.entity.User;
import com.training.library_catalog_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner {

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

private final PasswordEncoder encoder;
private final UserRepository userRepository;

    @Override
    public void run (String...args )//passed as an array/sequence of arguments
    {
        String email=adminEmail.trim().toLowerCase();

        if(userRepository.existsByEmail(email)){
            System.out.println("Admin account already exists");
            return;
        }

        User admin=new User();
        admin.setName("Administrator");
        admin.setEmail(email);
        admin.setPasswordHash(encoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);
        System.out.println("Admin account created successfully");

    }}
