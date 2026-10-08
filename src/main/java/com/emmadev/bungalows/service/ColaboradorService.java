package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Colaborador.*;
import com.emmadev.bungalows.DTO.Confidencial.ConfidencialResponse;
import com.emmadev.bungalows.DTO.Direccion.DireccionResponse;
import com.emmadev.bungalows.DTO.Laboral.LaboralResponse;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.repository.ColaboradorRepository;
import com.emmadev.bungalows.utils.FechaUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor

public class ColaboradorService {
    private final ColaboradorRepository repository;
    private final LaboralService laboralService;
    private final ConfidencialService confidencialService;
    private final DireccionService direccionService;
    private final ColaboradorQueryService queryService;

    @Transactional
    public void saveColaborador(ColaboradorRequestComplete dto){

        // Registra el colaborador
        Colaborador colaborador = new Colaborador();
        colaborador.setNombre(dto.nombre());
        colaborador.setAp(dto.ap());
        colaborador.setAm(dto.am());
        colaborador.setFechaNacimiento(dto.fechaNacimiento());
        colaborador.setFotografia(dto.fotografia());
        colaborador.setEstadoCivil(dto.estadoCivil());
        repository.save(colaborador);

        // registramos y obtenemos el dto
        laboralService.save(dto, colaborador);
        confidencialService.save(dto, colaborador);
        direccionService.save(dto, colaborador);
    }

    public ColaboradorResponse getColaboradorSimple(Long id){

        Colaborador colaborador = queryService.getById(id);

        return getDto(colaborador);
    }

    protected ColaboradorResponse getDto(Colaborador colaborador){
        return new ColaboradorResponse(
                colaborador.getId(),
                colaborador.getNombre(),
                colaborador.getAp(),
                colaborador.getAm(),
                colaborador.getFechaNacimiento(),
                colaborador.getEstadoCivil().getNombre(),
                colaborador.getFotografia()
        );
    }

    @Transactional(readOnly = true)
    public List<ColaboradorLaboralResponse> getColaboradoresLaboral(){

        return repository.findAll().stream().map(
                colaborador -> new ColaboradorLaboralResponse(
                        colaborador.getId(),
                        colaborador.getNombreCompleto(),
                        FechaUtils.getAnios(colaborador.getFechaNacimiento()),
                        colaborador.getFotografia(),
                        FechaUtils.getAnios(colaborador.getLaboral().getFechaIngreso()),
                        colaborador.getLaboral().getFechaIngreso(),
                        colaborador.getLaboral().getSueldo(),
                        colaborador.getLaboral().getTurno().getNombre(),
                        colaborador.getLaboral().getDiaDescanso().getNombre(),
                        colaborador.getLaboral().getEntrada(),
                        colaborador.getLaboral().getSalida(),
                        colaborador.getLaboral().getPuesto().getNombre(),
                        colaborador.getLaboral().getUbicacion().getNombre()
                )
        ).toList();
    }

    // para mostrar toda la información de un colaborador.
    @Transactional(readOnly = true)
    public ColaboradorCompleteResponse getColaboradorById(Long id){

        Colaborador colaborador = queryService.getById(id);

        return new ColaboradorCompleteResponse(
                colaborador.getId(),
                colaborador.getNombreCompleto(),
                FechaUtils.getAnios(colaborador.getFechaNacimiento()),
                colaborador.getFotografia(),
                FechaUtils.getAnios(colaborador.getLaboral().getFechaIngreso()),
                colaborador.getLaboral().getFechaIngreso(),
                colaborador.getLaboral().getSueldo(),
                colaborador.getLaboral().getTurno().getNombre(),
                colaborador.getLaboral().getDiaDescanso().getNombre(),
                colaborador.getLaboral().getEntrada(),
                colaborador.getLaboral().getSalida(),
                colaborador.getLaboral().getPuesto().getNombre(),
                colaborador.getLaboral().getUbicacion().getNombre(),
                colaborador.getConfidencial().getCurp(),
                colaborador.getConfidencial().getRfc(),
                colaborador.getConfidencial().getNss(),

                "Calle "+
                        colaborador.getDireccion().getCalle()+", "+
                        colaborador.getDireccion().getColonia()+", "+
                        colaborador.getDireccion().getCuidad()+", "+
                        colaborador.getDireccion().getEstado().getNombre()+": "+
                        colaborador.getDireccion().getCp()

        );
    }

    public void update(ColaboradorPatch patch){

        Colaborador colaborador = queryService.getById(patch.id());

        if(patch.nombre() != null){
            colaborador.setNombre(patch.nombre());
        }

        if(patch.ap() != null){
            colaborador.setAp(patch.ap());
        }

        if(patch.am() !=  null){
            colaborador.setAm(patch.am());
        }

        if(patch.fechaNacimiento() != null){
            colaborador.setFechaNacimiento(patch.fechaNacimiento());
        }

        if(patch.estadoCivil() != null){
            colaborador.setEstadoCivil(patch.estadoCivil());
        }

        if(patch.fotografia() != null){
            colaborador.setFotografia(patch.fotografia());
        }

        repository.save(colaborador);
    }

}
