package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Laboral.LaboralPatch;
import com.emmadev.bungalows.DTO.Laboral.AntiguedadesProximas;
import com.emmadev.bungalows.DTO.Laboral.LaboralResponse;
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

    @PatchMapping
    public void update(@RequestBody @Valid LaboralPatch patch){
       service.update(patch);
    }
}
