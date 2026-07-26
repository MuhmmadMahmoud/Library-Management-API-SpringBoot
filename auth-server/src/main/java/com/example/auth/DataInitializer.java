package com.example.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        userRepository.save(new AppUser("member", passwordEncoder.encode("member123"), "MEMBER"));
        userRepository.save(new AppUser("librarian", passwordEncoder.encode("librarian123"), "LIBRARIAN"));
        userRepository.save(new AppUser("admin", passwordEncoder.encode("admin123"), "ADMIN"));
    }
}
