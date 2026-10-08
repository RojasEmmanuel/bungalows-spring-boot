package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Direccion.DireccionPatch;
import com.emmadev.bungalows.DTO.Direccion.DireccionResponse;
import com.emmadev.bungalows.service.DireccionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/direccion")
@AllArgsConstructor
public class DireccionController {

    private final DireccionService service;

    @PatchMapping
    public void update(@RequestBody @Valid DireccionPatch patch){
        service.update(patch);
    }

    @GetMapping("/{colaboradorId}")
    public DireccionResponse getDireccion(@PathVariable Long colaboradorId){
       return service.getDireccion(colaboradorId);
    }
}
