package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Colaborador.ColaboradorRequestComplete;
import com.emmadev.bungalows.DTO.Confidencial.ConfidencialPatch;
import com.emmadev.bungalows.DTO.Confidencial.ConfidencialResponse;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Confidencial;
import com.emmadev.bungalows.repository.ConfidencialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfidencialService {

    private final ConfidencialRepository repository;
    private final ColaboradorQueryService colaboradorQuery;

    public ConfidencialResponse save(ColaboradorRequestComplete dto, Colaborador colaborador){

        Confidencial confidencial = new Confidencial();
        confidencial.setCurp(dto.curp());
        confidencial.setNss(dto.nss());
        confidencial.setRfc(dto.rfc());
        confidencial.setColaborador(colaborador);

        repository.save(confidencial);

        return getDto(confidencial);
    }

    public void update(ConfidencialPatch patch){
        Colaborador colaborador = colaboradorQuery.getById(patch.colaboradorId());
        Confidencial confidencial = colaborador.getConfidencial();

        if(patch.curp() != null){
            confidencial.setCurp(patch.curp());
        }
        if(patch.nss() != null){
            confidencial.setNss(patch.nss());
        }
        if(patch.rfc() != null){
            confidencial.setRfc(patch.rfc());
        }

        repository.save(confidencial);
    }

    public ConfidencialResponse getByColaborador(Long colaboradorId){
        Colaborador colaborador = colaboradorQuery.getById(colaboradorId);
        return getDto(colaborador.getConfidencial());
    }

    protected ConfidencialResponse getDto(Confidencial confidencial){
        return new ConfidencialResponse(
                confidencial.getCurp(),
                confidencial.getNss(),
                confidencial.getRfc()
        );
    }
}
