package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.dto.Nomina.NominaListadoResponse;
import com.emmadev.bungalows.dto.Nomina.NominaRequest;
import com.emmadev.bungalows.dto.Nomina.NominaResumenResponse;
import com.emmadev.bungalows.service.NominaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/nominas")
@AllArgsConstructor
public class NominaController {

    private final NominaService service;

    @PostMapping
    public Long crearNomina(@RequestBody @Valid NominaRequest request){
        return service.crearNomina(request);
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

    @GetMapping("/resumen")
    public ResponseEntity<NominaResumenResponse> getResumen(
            @RequestParam(required = false) Integer ejercicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin
    ) {
        NominaResumenResponse resumen =
                service.getResumen(ejercicio, fechaInicio, fechaFin);

        return ResponseEntity.ok(resumen);
    }


    @GetMapping
    public ResponseEntity<Page<NominaListadoResponse>> getNominas(
            @RequestParam(required = false) Integer ejercicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin,
            @RequestParam(required = false) String colaborador,
            @PageableDefault(
                    size = 20,
                    sort = "fechaPago",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return ResponseEntity.ok(
                service.getNominas(
                        ejercicio, fechaInicio, fechaFin, colaborador, pageable
                )
        );
    }
}
