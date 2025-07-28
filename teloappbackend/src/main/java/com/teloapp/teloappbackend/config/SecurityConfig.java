package com.teloapp.teloappbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Configuración CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            
            // Deshabilitar CSRF para APIs REST
            .csrf(csrf -> csrf.disable())
            
            // Configuración de autorización
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos (sin autenticación)
                .requestMatchers(
                    "/",
                    "/error",
                    "/api/motels/**",
                    "/actuator/health",
                    "/actuator/info"
                ).permitAll()
                
                // Endpoints OAuth2
                .requestMatchers("/oauth2/**", "/login/**").permitAll()
                
                // Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            )
            
            // Configuración de manejo de excepciones
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            
            // Configuración de sesiones (stateless para API REST)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Configuración OAuth2 (opcional, si usas autenticación con Google)
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("http://localhost:5173/auth-success", true)
                .failureUrl("/login?error=true")
            )
            
            // Configuración de logout
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}