package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Vacacional.VacacionalResponse;
import com.emmadev.bungalows.service.VacacionalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/periodos-vacacionales")
@AllArgsConstructor
public class VacacionalController {

    private  final VacacionalService service;

    @GetMapping
    public List<VacacionalResponse> getPeriodosVacacionales(){
        return service.getPeriodosVacacionalesActivos();
    }
}
