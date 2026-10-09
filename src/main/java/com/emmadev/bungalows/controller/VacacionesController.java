package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Vacaciones.VacacionesPatch;
import com.emmadev.bungalows.DTO.Vacaciones.VacacionesRequest;
import com.emmadev.bungalows.DTO.Vacaciones.VacacionesResponse;
import com.emmadev.bungalows.DTO.Vacaciones.VacacionesSImpleResponse;
import com.emmadev.bungalows.service.VacacionesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vacaciones")
public class VacacionesController {

    private final VacacionesService service;

    @GetMapping
    public List<VacacionesResponse> listar(){
        return  service.listarVacaciones();
    }

    @PostMapping
    public void registrarVacaciones(@RequestBody @Valid VacacionesRequest request){
        service.registrarVacaciones(request);
    }

    @PatchMapping
    public void editarVacaciones(@RequestBody @Valid VacacionesPatch patch){
        service.editarVacaciones(patch);
    }

    @GetMapping("/{id}")
    public VacacionesSImpleResponse getVacaciones(@PathVariable Long id){
        return service.getVacacciones(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarVacaciones(@PathVariable Long id){
        service.eliminarVacaciones(id);
    }
}
