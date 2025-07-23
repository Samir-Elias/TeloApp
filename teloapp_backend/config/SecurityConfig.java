package com.teloapp.teloappbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Permite que cualquiera vea los moteles sin iniciar sesión
                .requestMatchers("/api/motels", "/api/motels/**").permitAll() 
                // Cualquier otra petición requiere que el usuario esté autenticado
                .anyRequest().authenticated() 
            )
            // Activa el login con proveedores OAuth2 (como Google)
            .oauth2Login(withDefaults());

        return http.build();
    }
}