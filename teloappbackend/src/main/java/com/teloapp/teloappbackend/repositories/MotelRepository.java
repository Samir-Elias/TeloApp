package com.teloapp.teloappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teloapp.teloappbackend.models.Motel;

@Repository
public interface MotelRepository extends JpaRepository<Motel, Long> {
    // JpaRepository nos proporciona automáticamente todos los métodos básicos:
    // save(), findById(), findAll(), delete(), etc.
    // 
    // También podemos agregar métodos personalizados aquí si es necesario:
    // List<Motel> findByVerifiedTrue();
    // List<Motel> findByNameContainingIgnoreCase(String name);
}