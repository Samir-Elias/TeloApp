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

}