package com.emmadev.bungalows.service;

import com.emmadev.bungalows.Enums.EstatusColaborador;
import com.emmadev.bungalows.entity.Ubicacion;
import com.emmadev.bungalows.repository.LaboralRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LaboralQueryService {

    private final LaboralRepository repository;

    public long getTotalColaboradoresByUbicacion(Ubicacion ubicacion){
        return repository.findByUbicacion(ubicacion).stream().count();
    }
    public long getTotalColaboradoresActivosByUbicacion(Ubicacion ubicacion){
        return repository.findByUbicacion(ubicacion)
                .stream()
                .filter(laboral -> laboral.getEstatus() == EstatusColaborador.ACTIVO)
                .count();
    }
}
