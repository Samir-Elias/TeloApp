package com.teloapp.teloapp_backend.controllers;

import com.teloapp.teloapp_backend.models.Motel;
import com.teloapp.teloapp_backend.repositories.MotelRepository;

import org.springframework.core.env.Environment;
//post
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//put
import org.springframework.web.bind.annotation.PutMapping;
//delete
import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/motels")
public class MotelController {

    @Autowired
    private MotelRepository motelRepository;

    // --- INYECCIÓN DEL ENTORNO PARA LEER LA CONFIGURACIÓN ---
    @Autowired
    private Environment env;

    // --- NUEVO MÉTODO DE DIAGNÓSTICO ---
    @GetMapping("/debug-ddl")
    public String getDdlAutoConfig() {
        String ddlAuto = env.getProperty("spring.jpa.hibernate.ddl-auto");
        if (ddlAuto == null) {
            return "La propiedad 'spring.jpa.hibernate.ddl-auto' NO está configurada.";
        }
        return "La configuración actual de ddl-auto es: " + ddlAuto;
    }

    @GetMapping
    public List<Motel> getAllMotels() {
        return motelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Motel getMotelById(@PathVariable Long id) {
        return motelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el motel con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Motel> createMotel(@RequestBody Motel motel) {
        Motel nuevoMotel = motelRepository.save(motel);
        return new ResponseEntity<>(nuevoMotel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Motel updateMotel(@PathVariable Long id, @RequestBody Motel motelDetails) {
        Motel motelExistente = motelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el motel con ID: " + id));

        motelExistente.setName(motelDetails.getName());
        motelExistente.setAddress(motelDetails.getAddress());
        motelExistente.setLatitude(motelDetails.getLatitude());
        motelExistente.setLongitude(motelDetails.getLongitude());
        motelExistente.setPhone(motelDetails.getPhone());
        motelExistente.setDescription(motelDetails.getDescription());
        motelExistente.setVerified(motelDetails.isVerified());

        return motelRepository.save(motelExistente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotel(@PathVariable Long id) {
        if (!motelRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el motel con ID: " + id);
        }
        
        motelRepository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }
}
