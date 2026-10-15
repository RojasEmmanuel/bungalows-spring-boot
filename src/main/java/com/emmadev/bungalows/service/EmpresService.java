package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Empresa.EmpresaPatch;
import com.emmadev.bungalows.DTO.Empresa.EmpresaRequest;
import com.emmadev.bungalows.DTO.Empresa.EmpresaResponse;
import com.emmadev.bungalows.entity.Empresa;
import com.emmadev.bungalows.repository.EmpresaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EmpresService {

    private final EmpresaRepository repository;
    private final EmpresaQueryService queryService;

    @Transactional
    public void registrarEmpresa(EmpresaRequest request){
        Empresa empresa = new Empresa();
        empresa.setNombreLegal(request.nombreLegal());
        empresa.setResponsable(request.responsable());
        empresa.setRegimenFiscal(request.regimenFiscal());
        empresa.setDireccion(request.direccion());
        empresa.setRegistroPatronal(request.registroPatronal());
        empresa.setLogoPath(request.logoPath());

        repository.save(empresa);
    }

    @Transactional(readOnly = true)
    public List<EmpresaResponse> getEmpresas(){

        return repository.findAll().stream().map(this::getDto).toList();
    }

    @Transactional(readOnly = true)
    public EmpresaResponse getEmpresaById(Long id){
        return getDto(queryService.getById(id));
    }

    @Transactional
    public void actualizarEmpresa(EmpresaPatch patch){

        Empresa empresa = queryService.getById(patch.id());

        if(patch.registroPatronal() != null){
            empresa.setRegistroPatronal(patch.registroPatronal());
        }

        if (patch.direccion() != null) {
            empresa.setDireccion(patch.direccion());
        }

        if(patch.logoPath() != null){
            empresa.setLogoPath(patch.logoPath());
        }

        repository.save(empresa);
    }

    private EmpresaResponse getDto(Empresa empresa){
        return new EmpresaResponse(
                empresa.getId(),
                empresa.getNombreLegal(),
                empresa.getResponsable(),
                empresa.getRegimenFiscal(),
                empresa.getDireccion(),
                empresa.getRegistroPatronal(),
                empresa.getLogoPath()
        );
    }

}
