package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Documento.DocumentoRequest;
import com.emmadev.bungalows.DTO.Documento.DocumentoResponse;
import com.emmadev.bungalows.entity.Documento;
import com.emmadev.bungalows.repository.DocumentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor

public class DocumentoService {
    private final DocumentoRepository repository;
    private final ColaboradorQueryService queryService;

    //registra un documento y lo asocia a un colaborador.
    @Transactional
    public DocumentoResponse save(DocumentoRequest request){

        Documento documento = new Documento();
        documento.setNombre(request.nombre());
        documento.setPath(request.path());
        documento.setColaborador(queryService.getById(request.colaboradorId()));

        repository.save(documento);
        return getDto(documento);
    }

    // lista los documentos de un colaborador.
    @Transactional(readOnly = true)
    public List<DocumentoResponse> listar(Long colaboradorId){

        return repository.findByColaborador(queryService.getById(colaboradorId))
            .stream()
            .map(this::getDto)
            .toList();
    }

    // elimina un documento a apartir de su id
    public void eliminar(Long id){

        Documento documento = repository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("NO existe un documento con este ID"));

        repository.delete(documento);
    }

    private DocumentoResponse getDto(Documento documento){
        return new DocumentoResponse(
                documento.getId(),
                documento.getNombre(),
                documento.getPath()
        );
    }
}
