package com.teloapp.teloapp_backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Le dice a Spring que esta clase es una tabla de la base de datos.
@Table(name = "motels") // Le da el nombre "motels" a la tabla.
@Data // Lombok: crea automáticamente getters, setters, toString(), etc.
public class Motel {

    @Id // Marca este campo como la clave primaria (Primary Key).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea autoincremental.
    private Long id;

    @Column(nullable = false) // La columna no puede ser nula.
    private String name;

    private String address;

    private Double latitude;

    private Double longitude;



    private String phone;

    @Column(columnDefinition = "TEXT") // Define la columna como tipo TEXT para descripciones largas.
    private String description;

    private boolean verified = false; // Por defecto, un motel no está verificado.

}