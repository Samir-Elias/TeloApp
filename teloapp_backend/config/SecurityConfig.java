package com.teloapp.teloapp_backend.config; // Asegúrate de que este sea el paquete correcto.
                                          // En tu código anterior era com.teloapp.teloappbackend.config,
                                          // ahora lo cambié a com.teloapp.teloapp_backend.config para coincidir con la convención.
                                          // Si tu paquete es otro, ajústalo.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint; // Importar HttpStatusEntryPoint

// Importaciones necesarias para CorsFilter y UrlBasedCorsConfigurationSource
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays; // Para Arrays.asList

@Configuration
@EnableWebSecurity // Habilita la seguridad web en tu aplicación
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (CRÍTICO para APIs REST consumidas por otros dominios)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/error", "/webjars/**", // Rutas generales
                    "/api/motels", "/api/motels/**", // Permitir acceso a los moteles sin autenticación
                    "/oauth2/**", // Permite el acceso a las URLs de inicio del flujo OAuth2 (ej. /oauth2/authorization/google)
                    "/loginFailure" // Rutas para manejo de errores de login
                ).permitAll() // Estas rutas son accesibles sin autenticación
                .anyRequest().authenticated() // Todas las demás rutas requieren autenticación
            )
            .exceptionHandling(e -> e
                // Para solicitudes no autenticadas, devuelve 401 UNAUTHORIZED en lugar de redirigir a una página de login HTML
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .oauth2Login(oauth2 -> oauth2
                // URL a la que redirigir después de un login exitoso.
                // Esta DEBE ser una URL en tu FRONTEND React.
                // Usamos localhost:5173 para desarrollo.
                // IMPORTANTE: En producción, cambia esto a tu URL de Vercel (https://teloappreact-d71ewx86k-samir-elias-projects.vercel.app/auth-success o similar)
                .defaultSuccessUrl("http://localhost:5173/auth-success", true)
                .failureUrl("/loginFailure") // URL a la que redirigir en caso de login fallido
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Redirige a la raíz después de cerrar sesión
                .permitAll() // Permite que cualquier usuario acceda a la URL de logout
            );

        return http.build();
    }

    // --- Configuración explícita de CORS para Spring Security ---
    // Aunque ya tienes una CorsConfig, a veces Spring Security necesita su propia configuración de CORS
    // o puede interferir. Es una buena práctica tenerla aquí también o verificar la interacción.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Permite el envío de cookies/credenciales
        // Orígenes permitidos - Añade todos los dominios de tu frontend
        config.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:5173", // Desarrollo local React/Vite
            "https://teloapp-react-kbp21dzsd-samir-elias-projects.vercel.app", // Tu dominio principal de Vercel
            "https://teloappreact-d71ewx86k-samir-elias-projects.vercel.app" // Tu URL actual de Vercel Preview
            // Puedes añadir más si Vercel genera otras previews con diferentes hashes
        ));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization", "X-Requested-With"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", config); // Aplica a todas las rutas
        return new CorsFilter(source);
    }
}