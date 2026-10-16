package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.dto.Puesto.PuestoRequest;
import com.emmadev.bungalows.dto.Puesto.PuestoResponse;
import com.emmadev.bungalows.service.PuestoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/puestos")
public class PuestoController {

    private final PuestoService service;

    @GetMapping
    public List<PuestoResponse> listar(){
        return service.getPuestos();
    }

    @PostMapping
    public PuestoResponse registrar(@RequestBody @Valid PuestoRequest puesto){
        return service.save(puesto);
    }

    @DeleteMapping("/{id}")
    public void  eliminar(@PathVariable Long id){
        service.delete(id);
    }
}
