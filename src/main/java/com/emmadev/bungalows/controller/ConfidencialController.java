package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Confidencial.ConfidencialPatch;
import com.emmadev.bungalows.DTO.Confidencial.ConfidencialResponse;
import com.emmadev.bungalows.service.ConfidencialService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/confidencial")
@AllArgsConstructor
public class ConfidencialController {

    private final ConfidencialService service;

    @GetMapping("/{colaboradorId}")
    public ConfidencialResponse getByColaborador(@PathVariable Long colaboradorId){
        return service.getByColaborador(colaboradorId);
    }

    @PatchMapping
    public void update(@RequestBody @Valid ConfidencialPatch patch){
        service.update(patch);
    }
}
