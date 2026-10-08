package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Colaborador.ColaboradorRequestComplete;
import com.emmadev.bungalows.DTO.Direccion.DireccionPatch;
import com.emmadev.bungalows.DTO.Direccion.DireccionResponse;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Direccion;
import com.emmadev.bungalows.repository.DireccionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DireccionService {

    private final DireccionRepository repository;
    private final ColaboradorQueryService colaboradorQuery;

    public DireccionResponse save(ColaboradorRequestComplete dto, Colaborador colaborador){
        Direccion direccion = new Direccion();
        direccion.setColaborador(colaborador);
        direccion.setCp(dto.cp());
        direccion.setCalle(dto.calle());
        direccion.setColonia(dto.colonia());
        direccion.setCuidad(dto.ciudad());
        direccion.setEstado(dto.estado());
        repository.save(direccion);

        return getDto(direccion);
    }

    public void update(DireccionPatch patch){
        Colaborador colaborador = colaboradorQuery.getById(patch.colaboradorId());
        Direccion direccion = colaborador.getDireccion();

        if(patch.calle() != null){
            direccion.setCalle(patch.calle());
        }

        if(patch.colonia() != null){
            direccion.setColonia(patch.colonia());
        }

        if(patch.ciudad() != null){
            direccion.setCuidad(patch.ciudad());
        }

        if(patch.estado() != null){
            direccion.setEstado(patch.estado());
        }

        if(patch.cp() != null){
            direccion.setCp(patch.cp());
        }

        repository.save(direccion);
    }

    public DireccionResponse getDireccion(Long colaboradorId){
        Colaborador colaborador = colaboradorQuery.getById(colaboradorId);

        return getDto(colaborador.getDireccion());
    }

    protected DireccionResponse getDto(Direccion direccion){
        return new DireccionResponse(
                direccion.getCalle(),
                direccion.getColonia(),
                direccion.getCuidad(),
                direccion.getEstado(),
                direccion.getCp()
        );
    }
}
