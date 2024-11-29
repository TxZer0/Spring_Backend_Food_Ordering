package org.example.nuiifo0d.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nuiifo0d.entity.User;
import org.example.nuiifo0d.enums.Role;
import org.example.nuiifo0d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Objects;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class AdminConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${account.admin.email}")
    private String email;

    @Value("${account.admin.username}")
    private String username;

    @Value("${account.admin.password}")
    private String password;

    @Bean
    ApplicationRunner createAdminAccount(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin") == null) {
                User user = User.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .role(Role.ADMIN)
                        .email(email)
                        .build();
                userRepository.save(user);
                log.warn("Admin account created with default password: {}", password);
            }
        };
    }
}