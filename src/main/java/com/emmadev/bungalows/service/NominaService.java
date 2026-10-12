package com.emmadev.bungalows.service;


import com.emmadev.bungalows.DTO.Nomina.NominaRequest;
import com.emmadev.bungalows.repository.NominaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NominaService {

    private final NominaRepository repository;

    public void crearNomina(NominaRequest request){


    }

}
