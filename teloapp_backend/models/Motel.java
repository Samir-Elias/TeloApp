package com.teloapp.teloappbackend.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List; // Importa List si vas a usarlo para servicios

@Entity
@Table(name = "motels")
@Data
public class Motel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;
    private Double latitude;
    private Double longitude;
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean verified = false;

    // --- AGREGA ESTA LÍNEA AQUÍ ---
    private boolean openNow;

    // --- (Opcional) Si quieres añadir los otros campos que usamos en el frontend ---
     private Double rating;
     private Integer reviewsCount;
     private String priceRange;
     @ElementCollection // Para una lista de strings simples
     private List<String> services;
}