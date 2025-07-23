package com.teloapp.teloapp_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", // Tu frontend de desarrollo
                                "https://teloapp-react-kbp21dzsd-samir-elias-projects.vercel.app", // Tu dominio principal de Vercel (si lo tienes configurado)
                                "https://teloappreact-d71ewx86k-samir-elias-projects.vercel.app" // <-- ¡ESTA ES LA URL ACTUAL QUE NECESITAS AÑADIR!
                                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}