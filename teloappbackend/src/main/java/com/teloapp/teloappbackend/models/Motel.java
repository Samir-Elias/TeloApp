package com.teloapp.teloappbackend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "motels")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private boolean verified = false;

    @Column(name = "open_now")
    private boolean openNow = true;

    private Double rating;
    
    @Column(name = "reviews_count")
    private Integer reviewsCount = 0;
    
    @Column(name = "price_range")
    private String priceRange;
    
    // Para almacenar servicios como lista separada por comas
    @ElementCollection
    @CollectionTable(name = "motel_services", joinColumns = @JoinColumn(name = "motel_id"))
    @Column(name = "service")
    private List<String> services;
    
    // Constructor personalizado para facilitar la creación
    public Motel(String name, String address, Double latitude, Double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.verified = false;
        this.openNow = true;
        this.reviewsCount = 0;
    }
}