package com.teloapp.teloappbackend;

import com.teloapp.teloappbackend.models.Motel;
import com.teloapp.teloappbackend.repositories.MotelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Profile; // Esta línea (el import) también debería desaparecer si no la usas

@SpringBootApplication
// ¡Esta línea @Profile("dev") DEBE HABER SIDO ELIMINADA!
public class TeloappBackendApplication implements CommandLineRunner {
    // ... el resto de tu código ...
}