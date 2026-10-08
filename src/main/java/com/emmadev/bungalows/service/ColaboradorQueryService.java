package com.emmadev.bungalows.service;

import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.repository.ColaboradorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ColaboradorQueryService {

    private final ColaboradorRepository repository;

    public Colaborador getById(Long id){
        return repository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("No existe un colaborador con este ID")
        );
    }
}
