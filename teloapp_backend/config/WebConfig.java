package com.teloapp.teloappbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // CAMBIO CLAVE: Ahora permitimos dos orígenes
                .allowedOrigins(
                    "http://localhost:5173", // Para desarrollo en tu PC
                    "https://telo-app-deployment.vercel.app" // La URL de tu app en Vercel
                ) 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}