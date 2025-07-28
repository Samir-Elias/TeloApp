package com.teloapp.teloappbackend.controllers;

import com.teloapp.teloappbackend.models.Motel;
import com.teloapp.teloappbackend.repositories.MotelRepository;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/motels")
@CrossOrigin(origins = {
    "http://localhost:5173", 
    "https://teloappreact-d71ewx86k-samir-elias-projects.vercel.app",
    "https://telo-app-deployment.vercel.app"
})
public class MotelController {

    @Autowired
    private MotelRepository motelRepository;

    @Autowired
    private Environment env;

    // Endpoint de diagnóstico
    @GetMapping("/debug-ddl")
    public ResponseEntity<String> getDdlAutoConfig() {
        try {
            String ddlAuto = env.getProperty("spring.jpa.hibernate.ddl-auto");
            String response = ddlAuto != null ? 
                "La configuración actual de ddl-auto es: " + ddlAuto : 
                "La propiedad 'spring.jpa.hibernate.ddl-auto' NO está configurada.";
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener configuración: " + e.getMessage());
        }
    }

    // Obtener todos los moteles
    @GetMapping
    public ResponseEntity<List<Motel>> getAllMotels() {
        try {
            List<Motel> motels = motelRepository.findAll();
            return ResponseEntity.ok(motels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener motel por ID
    @GetMapping("/{id}")
    public ResponseEntity<Motel> getMotelById(@PathVariable Long id) {
        try {
            Optional<Motel> motel = motelRepository.findById(id);
            return motel.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nuevo motel
    @PostMapping
    public ResponseEntity<Motel> createMotel(@RequestBody Motel motel) {
        try {
            // Validaciones básicas
            if (motel.getName() == null || motel.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            Motel nuevoMotel = motelRepository.save(motel);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMotel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar motel
    @PutMapping("/{id}")
    public ResponseEntity<Motel> updateMotel(@PathVariable Long id, @RequestBody Motel motelDetails) {
        try {
            Optional<Motel> motelOptional = motelRepository.findById(id);
            
            if (!motelOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Motel motelExistente = motelOptional.get();
            
            // Actualizar campos solo si no son null
            if (motelDetails.getName() != null) {
                motelExistente.setName(motelDetails.getName());
            }
            if (motelDetails.getAddress() != null) {
                motelExistente.setAddress(motelDetails.getAddress());
            }
            if (motelDetails.getLatitude() != null) {
                motelExistente.setLatitude(motelDetails.getLatitude());
            }
            if (motelDetails.getLongitude() != null) {
                motelExistente.setLongitude(motelDetails.getLongitude());
            }
            if (motelDetails.getPhone() != null) {
                motelExistente.setPhone(motelDetails.getPhone());
            }
            if (motelDetails.getDescription() != null) {
                motelExistente.setDescription(motelDetails.getDescription());
            }
            if (motelDetails.getRating() != null) {
                motelExistente.setRating(motelDetails.getRating());
            }
            if (motelDetails.getPriceRange() != null) {
                motelExistente.setPriceRange(motelDetails.getPriceRange());
            }
            if (motelDetails.getServices() != null) {
                motelExistente.setServices(motelDetails.getServices());
            }
            
            motelExistente.setVerified(motelDetails.isVerified());
            motelExistente.setOpenNow(motelDetails.isOpenNow());

            Motel motelActualizado = motelRepository.save(motelExistente);
            return ResponseEntity.ok(motelActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar motel
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotel(@PathVariable Long id) {
        try {
            if (!motelRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            
            motelRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint de health check
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API funcionando correctamente");
    }
}