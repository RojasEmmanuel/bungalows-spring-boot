package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.dto.Empresa.EmpresaPatch;
import com.emmadev.bungalows.dto.Empresa.EmpresaRequest;
import com.emmadev.bungalows.dto.Empresa.EmpresaResponse;
import com.emmadev.bungalows.service.EmpresService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@AllArgsConstructor
public class EmpresaController {

    private final EmpresService service;

    @PostMapping
    public void registrarEmpresa(@RequestBody @Valid EmpresaRequest request){
        service.registrarEmpresa(request);
    }

    @GetMapping
    public List<EmpresaResponse> getEmpresas(){
        return service.getEmpresas();
    }

    @GetMapping("/{id}")
    public EmpresaResponse getEmpresaById(@PathVariable Long id){
        return service.getEmpresaById(id);
    }

    @PatchMapping
    public void actualizarEmpresa(@RequestBody EmpresaPatch patch){
        service.actualizarEmpresa(patch);
    }
}
