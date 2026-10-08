package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Prestamo.PrestamoPatch;
import com.emmadev.bungalows.DTO.Prestamo.PrestamoRequest;
import com.emmadev.bungalows.DTO.Prestamo.PrestamoResponse;
import com.emmadev.bungalows.DTO.Prestamo.PrestamoSimpleResponse;
import com.emmadev.bungalows.service.PrestamoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService service;

    @GetMapping
    public List<PrestamoResponse> allPrestamos(){
        return service.getPrestamos();
    }

    @GetMapping("/{id}")
    public PrestamoSimpleResponse prestamo(@PathVariable  Long id){
        return service.getPrestamo(id);
    }

    @PostMapping
    public PrestamoResponse registrar(@RequestBody @Valid PrestamoRequest request){
        return service.savePrestamo(request);
    }

    @PatchMapping
    public PrestamoResponse actualizar(@RequestBody @Valid PrestamoPatch patch){
        return service.actualizarPrestamo(patch);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
         service.eliminarPrestamo(id);
    }
}
