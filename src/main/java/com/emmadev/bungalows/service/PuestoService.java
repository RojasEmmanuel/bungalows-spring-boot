package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Puesto.PuestoRequest;
import com.emmadev.bungalows.DTO.Puesto.PuestoResponse;
import com.emmadev.bungalows.entity.Puesto;
import com.emmadev.bungalows.repository.PuestoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PuestoService {

    private final PuestoRepository repository;

    public List<PuestoResponse> getPuestos(){
        return repository.findAll().stream().map(
            puesto -> new PuestoResponse(
                puesto.getId(),
                puesto.getNombre(),
                puesto.getDescripcion()
            )
        ).toList();
    }

    protected Puesto getById(Long id){
        return repository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("no existe un puesto con este id")
        );
    }

    public PuestoResponse save(PuestoRequest request){
        Puesto puesto = new Puesto();
        puesto.setNombre(request.nombre());
        puesto.setDescripcion(request.descripcion());

        repository.save(puesto);

        return new PuestoResponse(
                puesto.getId(),
                puesto.getNombre(),
                puesto.getDescripcion()
        );
    }

    public void delete(Long id){
        Puesto puesto = getById(id);
        repository.delete(puesto);
    }
}
