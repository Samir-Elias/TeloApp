package com.teloapp.teloapp_backend.controllers;

import com.teloapp.teloapp_backend.models.Motel;
import com.teloapp.teloapp_backend.repositories.MotelRepository;

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
import org.springframework.web.bind.annotation.PathVariable; // Asegúrate de importar esto
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
//import java.util.Optional; // Y esto también

@RestController
@RequestMapping("/api/motels")
public class MotelController {

    @Autowired
    private MotelRepository motelRepository;

    @GetMapping
    public List<Motel> getAllMotels() {
        return motelRepository.findAll();
    }

    // --- AGREGA ESTE NUEVO MÉTODO ---
    /**
     * Maneja las peticiones GET a /api/motels/{id}
     * @param id El ID del motel que viene en la URL.
     * @return Los datos del motel encontrado.
     */
    @GetMapping("/{id}")
    public Motel getMotelById(@PathVariable Long id) {
        // Usamos findById que devuelve un Optional. Si no lo encuentra,
        // podemos lanzar una excepción para que se muestre un error.
        return motelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el motel con ID: " + id));
    }


/**
 * Maneja las peticiones POST a /api/motels para crear un nuevo motel.
 //param motel El objeto Motel que viene en el cuerpo (body) de la petición en formato JSON.
 //return El motel guardado, incluyendo el nuevo ID generado, con un código de estado 201 (Created).
 */


@PostMapping
public ResponseEntity<Motel> createMotel(@RequestBody Motel motel) {
    Motel nuevoMotel = motelRepository.save(motel);
    return new ResponseEntity<>(nuevoMotel, HttpStatus.CREATED);
}


 //Maneja las peticiones PUT a /api/motels/{id} para actualizar un motel.
 //@param id El ID del motel a actualizar, que viene en la URL.
 // @param motelDetails Los nuevos datos del motel, que vienen en el cuerpo de la petición.
 //return El objeto Motel ya actualizado.



@PutMapping("/{id}")
public Motel updateMotel(@PathVariable Long id, @RequestBody Motel motelDetails) {
    // 1. Buscamos el motel que se quiere actualizar.
    Motel motelExistente = motelRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el motel con ID: " + id));

    // 2. Actualizamos cada campo del motel existente con los datos nuevos.
    motelExistente.setName(motelDetails.getName());
    motelExistente.setAddress(motelDetails.getAddress());
    motelExistente.setLatitude(motelDetails.getLatitude());
    motelExistente.setLongitude(motelDetails.getLongitude());
    motelExistente.setPhone(motelDetails.getPhone());
    motelExistente.setDescription(motelDetails.getDescription());
    motelExistente.setVerified(motelDetails.isVerified());

    // 3. Guardamos los cambios en la base de datos.
    // Como el objeto ya tiene un ID, JpaRepository lo actualiza en lugar de crearlo.
    return motelRepository.save(motelExistente);
}
/* Maneja las peticiones DELETE a /api/motels/{id} para eliminar un motel.
 * @param id El ID del motel a eliminar.
 * @return Una respuesta vacía con código de estado 204 (No Content).
 */
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteMotel(@PathVariable Long id) {
    // Verificamos si el motel existe antes de intentar borrarlo.
    if (!motelRepository.existsById(id)) {
        throw new RuntimeException("No se encontró el motel con ID: " + id);
    }
    
    motelRepository.deleteById(id);
    
    // Devolvemos una respuesta con código 204 (No Content) para indicar que se borró con éxito.
    return ResponseEntity.noContent().build();
}
}