package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Nomina.DeduccionRequest;
import com.emmadev.bungalows.DTO.Nomina.NominaRequest;
import com.emmadev.bungalows.DTO.Nomina.PercepcionRequest;
import com.emmadev.bungalows.service.NominaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nominas")
@AllArgsConstructor
public class NominaController {

    private final NominaService service;

    @PostMapping
    public void crearNomina(@RequestBody @Valid NominaRequest request){
        service.crearNomina(request);
    }
}
