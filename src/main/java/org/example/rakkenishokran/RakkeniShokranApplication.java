package org.example.rakkenishokran;

import org.example.rakkenishokran.Entities.User;
import org.example.rakkenishokran.Enums.Role;
import org.example.rakkenishokran.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@EnableScheduling
public class RakkeniShokranApplication {


    public static void main(String[] args) {
        SpringApplication.run(RakkeniShokranApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            try{
                userRepository.findByUsername("superAdmin");
            }
            catch(EmptyResultDataAccessException e){
                User superAdmin = User.builder()
                        .username("superAdmin")
                        .password(passwordEncoder.encode("password"))
                        .email("superAdmin@test.com")
                        .phoneNumber("000000000")
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(superAdmin);
            }
        };
    }
}