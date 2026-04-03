package com.sasms.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordTestRunner {

    @Bean
    CommandLineRunner testPassword(PasswordEncoder encoder) {
        return args -> {
            String raw = "password123";
            String hashed = encoder.encode(raw);

//            System.out.println("RAW: " + raw);
//            System.out.println("HASHED: " + hashed);
//            System.out.println("MATCHES: " + encoder.matches(raw, hashed));
        };
    }
}
