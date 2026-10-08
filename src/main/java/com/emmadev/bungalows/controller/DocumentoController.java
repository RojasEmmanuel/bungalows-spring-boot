package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.DTO.Documento.DocumentoRequest;
import com.emmadev.bungalows.DTO.Documento.DocumentoResponse;
import com.emmadev.bungalows.service.DocumentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@AllArgsConstructor
public class DocumentoController {

    private final DocumentoService service;

    @PostMapping
    public DocumentoResponse registrar(@RequestBody @Valid DocumentoRequest request){
        return service.save(request);
    }

    @GetMapping("/{colaboradorId}")
    public List<DocumentoResponse> listar(@PathVariable Long colaboradorId){
        return service.listar(colaboradorId);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}
