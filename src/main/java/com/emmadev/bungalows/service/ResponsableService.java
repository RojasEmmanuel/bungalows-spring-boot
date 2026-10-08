package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Responsable.ResponsablePatch;
import com.emmadev.bungalows.DTO.Responsable.ResponsableRequest;
import com.emmadev.bungalows.DTO.Responsable.ResponsableResponse;
import com.emmadev.bungalows.entity.Responsable;
import com.emmadev.bungalows.repository.ResponsableRepository;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ResponsableService {

    private final ResponsableRepository repository;
    private final ColaboradorQueryService colaboradorQuery;

    @Transactional
    public ResponsableResponse save(ResponsableRequest request){

        Responsable responsable = new Responsable();
        responsable.setNombre(request.nombre());
        responsable.setParentesco(request.parentesco());
        responsable.setTelefono(request.telefono());
        responsable.setColaborador(colaboradorQuery.getById(request.colaboradorId()));

        repository.save(responsable);
        return getDto(responsable);
    }

    @Transactional(readOnly = true)
    public List<ResponsableResponse> listar(Long colaboradorId){

        return repository.findByColaborador(colaboradorQuery.getById(colaboradorId))
                .stream()
                .map(this::getDto)
                .toList();
    }

    @Transactional
    public ResponsableResponse update(ResponsablePatch patch){

        Responsable responsable = getById(patch.id());

        if(patch.nombre() != null){
            responsable.setNombre(patch.nombre());
        }

        if(patch.parentesco() != null){
            responsable.setParentesco(patch.parentesco());
        }

        if(patch.telefono() != null){
            responsable.setTelefono(patch.telefono());
        }

        repository.save(responsable);
        return getDto(responsable);
    }

    private Responsable getById(Long id){
        return repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("NO existe un responsable con este id")
        );
    }

    private ResponsableResponse getDto(Responsable responsable){
        return new ResponsableResponse(
                responsable.getId(),
                responsable.getNombre(),
                responsable.getParentesco().getNombre(),
                responsable.getTelefono()
        );
    }
}
