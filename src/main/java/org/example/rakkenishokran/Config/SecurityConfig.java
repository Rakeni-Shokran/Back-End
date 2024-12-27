package org.example.rakkenishokran.Config;


import org.example.rakkenishokran.Authorization.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // to enable the web security
@EnableMethodSecurity // to enable the method security such as @PreAuthorize
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
//    private final LogOutService logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/authenticate/**") // list of request that should be permitted
                        .permitAll()
                        .requestMatchers("/pm/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/driver/**") // list of request that should be permitted
                        .hasRole("DRIVER")
                        .requestMatchers("/parkingLot/getAll") // list of request that should be permitted
                        .permitAll()
                        .anyRequest() // any other request should be authenticated
                        .authenticated()
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // to make the session stateless
                )
                .authenticationProvider(authenticationProvider) // to add the authentication provider
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // to add the filter before the UsernamePasswordAuthenticationFilter
        ;

        return http.build();
    }
}
