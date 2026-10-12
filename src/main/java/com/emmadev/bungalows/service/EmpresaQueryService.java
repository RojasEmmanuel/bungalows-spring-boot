package com.emmadev.bungalows.service;

import com.emmadev.bungalows.entity.Empresa;
import com.emmadev.bungalows.repository.EmpresaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmpresaQueryService {

    private final EmpresaRepository repository;

    public Empresa getById(Long id){

        return  repository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("No existe una empresa con este id")
                );
    }
}
