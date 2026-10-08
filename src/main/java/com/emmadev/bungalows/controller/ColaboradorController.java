package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Colaborador.*;
import com.emmadev.bungalows.service.ColaboradorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/colaboradores")
public class ColaboradorController {

    private final ColaboradorService service;

    @PostMapping
    public void postColaboradores(@RequestBody @Valid ColaboradorRequestComplete dto){
         service.saveColaborador(dto);
    }

    @GetMapping
    public List<ColaboradorLaboralResponse> getColaboradores(){
        return service.getColaboradoresLaboral();
    }

    @GetMapping("/{id}")
    public ColaboradorCompleteResponse getColaborador(@PathVariable Long id){
        return service.getColaboradorById(id);
    }

    @GetMapping("/simple/{id}")
    public ColaboradorResponse getColaboradorSimple(@PathVariable Long id){
        return service.getColaboradorSimple(id);
    }

    @PatchMapping
    public void update(@RequestBody @Valid ColaboradorPatch patch){
        service.update(patch);
    }
}
