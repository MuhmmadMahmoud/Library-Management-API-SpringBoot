package com.example.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryConfig {

    private static final Logger logger = LoggerFactory.getLogger(LibraryConfig.class);

    @Bean
    public String welcomeMessage(LibraryProperties properties) {
        String message = "Welcome to " + properties.getName() + " - " + properties.getDescription();
        logger.info("Custom Bean Created: " + message);
        return message;
    }
}
