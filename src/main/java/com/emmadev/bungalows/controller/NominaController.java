package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Nomina.DeduccionRequest;
import com.emmadev.bungalows.DTO.Nomina.NominaRequest;
import com.emmadev.bungalows.DTO.Nomina.PercepcionRequest;
import com.emmadev.bungalows.service.NominaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nominas")
@AllArgsConstructor
public class NominaController {

    private final NominaService service;

    @PostMapping
    public void crearNomina(@RequestBody @Valid NominaRequest request){
        service.crearNomina(request);
    }


    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> pdf(@PathVariable Long id) {
        byte[] pdf = service.generarPdfNomina(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"nomina-" + id + ".pdf\"")
                .body(pdf);
    }
}
