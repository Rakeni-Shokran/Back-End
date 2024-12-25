package org.example.rakkenishokran;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class RakkeniShokranApplication {


    public static void main(String[] args) {
        System.out.println("Hello");
        SpringApplication.run(RakkeniShokranApplication.class, args);
        System.out.println("Goodbye");
    }

    @Bean
    CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate) {
        return args -> {
            System.out.println("Database initialization started...");
            // The script will be automatically executed by Spring Boot
            // due to the configuration in application.properties
            System.out.println("Database initialization completed.");
        };
    }
}
