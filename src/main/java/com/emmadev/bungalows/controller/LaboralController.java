package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Laboral.*;
import com.emmadev.bungalows.service.LaboralService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laboral")
@AllArgsConstructor
public class LaboralController {

    private final LaboralService service;

    @GetMapping("/{colaboradorId}")
    public LaboralResponse getLaboral(@PathVariable Long colaboradorId){
        return service.getByColaborador(colaboradorId);
    }

    @GetMapping("/antiguedades")
    public List<AntiguedadesProximas> antiguedadesProximas(){
        return service.getAntiguedadesProximas();
    }

    @GetMapping("/aniversarios")
    public List<Aniversarios> aniversarios(){
        return service.getAniversarios();
    }

    @GetMapping("/nomina/{colaboradorId}")
    public LaboralNominaResponse nominaResponse(@PathVariable Long colaboradorId){
        return service.getLaboralNominas(colaboradorId);
    }

    @PatchMapping
    public void update(@RequestBody @Valid LaboralPatch patch){
       service.update(patch);
    }
}
