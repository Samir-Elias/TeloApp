package com.teloapp.teloapp_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teloapp.teloapp_backend.models.Motel;

@Repository // Le indica a Spring que esta es una interfaz de repositorio.
public interface MotelRepository extends JpaRepository<Motel, Long> {
    // La magia ocurre aquí. Al extender JpaRepository, Spring nos da
    // automáticamente todos los métodos básicos para operar con la base de datos:
    // save(), findById(), findAll(), delete(), etc.
    //
    // <Motel, Long> significa que este repositorio manejará entidades de tipo "Motel"
    // y que la clave primaria de "Motel" es de tipo "Long".
}