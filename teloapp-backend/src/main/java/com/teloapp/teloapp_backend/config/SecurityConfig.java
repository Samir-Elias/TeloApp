package com.teloapp.teloapp_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Autoriza todas las peticiones HTTP, permitiendo el acceso sin autenticación.
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            // Desactiva la protección CSRF (Cross-Site Request Forgery), común para APIs stateless.
            .csrf(csrf -> csrf.disable());
        return http.build();
    }
}