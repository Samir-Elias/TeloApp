package com.teloapp.teloappbackend;

import com.teloapp.teloappbackend.models.Motel;
import com.teloapp.teloappbackend.repositories.MotelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Profile; // Ya no necesitas este import si quitas la línea de @Profile

@SpringBootApplication
// @Profile("dev") // ¡Eliminada!
public class TeloappBackendApplication implements CommandLineRunner {

    @Autowired
    private MotelRepository motelRepository;

    public static void main(String[] args) {
        SpringApplication.run(TeloappBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Limpiamos la base de datos por si acaso (opcional)
        motelRepository.deleteAll();

        // Creamos un par de moteles de prueba
        Motel motel1 = new Motel();
        motel1.setName("Motel El Secreto");
        motel1.setAddress("Ruta 7 km 1024, Luján de Cuyo");
        motel1.setLatitude(-32.987);
        motel1.setLongitude(-68.851);
        motel1.setPhone("261-456-7890");
        motel1.setDescription("Discreción y confort a minutos de la ciudad.");

        Motel motel2 = new Motel();
        motel2.setName("Susurros del Parque");
        motel2.setAddress("Av. Libertador 123, Las Heras");
        motel2.setLatitude(-32.885);
        motel2.setLongitude(-68.859);
        motel2.setPhone("261-123-4567");
        motel2.setDescription("El escape perfecto cerca del Parque General San Martín.");
        motel2.setVerified(true);

        // Guardamos los moteles en la base de datos
        motelRepository.save(motel1);
        motelRepository.save(motel2);

        System.out.println("✅ Datos de prueba cargados: 2 moteles guardados.");
    }
}