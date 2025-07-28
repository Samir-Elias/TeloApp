package com.teloapp.teloappbackend.config;

import com.teloapp.teloappbackend.models.Motel;
import com.teloapp.teloappbackend.repositories.MotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MotelRepository motelRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo inicializar datos si la base de datos está vacía
        if (motelRepository.count() == 0) {
            initializeMotelData();
        }
    }

    private void initializeMotelData() {
        List<Motel> moteles = Arrays.asList(
            createMotel(
                "Motel Paradise",
                "Av. San Martín 1234, Mendoza",
                -32.8895,
                -68.8258,
                "+54 261 123-4567",
                "Motel moderno con todas las comodidades. Habitaciones temáticas y jacuzzi.",
                4.5,
                "$$",
                Arrays.asList("WiFi", "Jacuzzi", "TV", "Aire Acondicionado", "Estacionamiento")
            ),
            
            createMotel(
                "Hotel Romance",
                "Calle Las Heras 567, Mendoza",
                -32.8920,
                -68.8285,
                "+54 261 234-5678",
                "Ambiente íntimo y romántico. Ideal para parejas que buscan privacidad.",
                4.2,
                "$$$",
                Arrays.asList("WiFi", "Hidromasaje", "TV Cable", "Minibar", "Room Service")
            ),
            
            createMotel(
                "Motel Luna",
                "Av. Mitre 890, Mendoza",
                -32.8880,
                -68.8290,
                "+54 261 345-6789",
                "Ubicación céntrica con fácil acceso. Habitaciones cómodas y discretas.",
                4.0,
                "$$",
                Arrays.asList("WiFi", "TV", "Aire Acondicionado", "Ducha Escocesa")
            ),
            
            createMotel(
                "Hotel Dreams",
                "Calle Sarmiento 345, Mendoza",
                -32.8910,
                -68.8240,
                "+54 261 456-7890",
                "Experiencia única con habitaciones de lujo y servicios premium.",
                4.3,
                "$$$",
                Arrays.asList("WiFi", "Jacuzzi", "TV Smart", "Champagne", "Sauna")
            ),
            
            createMotel(
                "Motel Estrella",
                "Av. Belgrano 678, Mendoza",
                -32.8925,
                -68.8275,
                "+54 261 567-8901",
                "Opción económica sin comprometer la calidad. Limpio y confortable.",
                3.9,
                "$",
                Arrays.asList("WiFi", "TV", "Ducha", "Estacionamiento")
            ),
            
            createMotel(
                "Hotel Secreto",
                "Calle Godoy Cruz 456, Mendoza",
                -32.8888,
                -68.8278,
                "+54 261 678-9012",
                "Máxima discreción y privacidad. Entrada y salida independientes.",
                4.1,
                "$$",
                Arrays.asList("WiFi", "TV", "Minibar", "Aire Acondicionado", "Entrada Privada")
            )
        );
        
        motelRepository.saveAll(moteles);
        System.out.println("✅ Datos de prueba inicializados: " + moteles.size() + " moteles agregados");
    }
    
    private Motel createMotel(String name, String address, Double lat, Double lng, 
                             String phone, String description, Double rating, 
                             String priceRange, List<String> services) {
        Motel motel = new Motel();
        motel.setName(name);
        motel.setAddress(address);
        motel.setLatitude(lat);
        motel.setLongitude(lng);
        motel.setPhone(phone);
        motel.setDescription(description);
        motel.setRating(rating);
        motel.setPriceRange(priceRange);
        motel.setServices(services);
        motel.setVerified(true);
        motel.setOpenNow(true);
        motel.setReviewsCount((int) (Math.random() * 50) + 10); // Entre 10 y 60 reviews
        
        return motel;
    }
}