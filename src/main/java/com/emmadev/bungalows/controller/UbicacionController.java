package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Ubicacion.UbicacionDetail;
import com.emmadev.bungalows.DTO.Ubicacion.UbicacionRequest;
import com.emmadev.bungalows.DTO.Ubicacion.UbicacionRequestPatch;
import com.emmadev.bungalows.DTO.Ubicacion.UbicacionResponse;
import com.emmadev.bungalows.service.UbicacionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/ubicaciones")
public class UbicacionController { // CONTROLADOR PARA MANEJAR OPERACION CRUD DE UBICACIONES

    private final UbicacionService service;

    @PostMapping
    public UbicacionResponse postUbicacion(@RequestBody @Valid UbicacionRequest ubicacion){
        return service.saveUbicacion(ubicacion);
    }

    @GetMapping
    public List<UbicacionResponse> getUbicaciones(){
        return service.getUbicaciones();
    }

    @GetMapping("/{id}")
    public UbicacionResponse getUbicacion(@PathVariable Long id){
        return service.getUbicacion(id);
    }

    @GetMapping("/detail")
    public List<UbicacionDetail> getUbicacionesDetail(){
        return service.ubicacionesDetail();
    }

    @PatchMapping
    public UbicacionResponse patchUbicacion(@RequestBody @Valid UbicacionRequestPatch ubicacion){
        return service.updateUbicacion(ubicacion);
    }

    @DeleteMapping("/{id}")
    public void eliminarUbicacion(@PathVariable long id){
        service.eliminarUbicacion(id);
    }
}
