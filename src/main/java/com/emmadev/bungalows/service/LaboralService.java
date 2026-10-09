package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Colaborador.ColaboradorRequestComplete;
import com.emmadev.bungalows.DTO.Laboral.Aniversarios;
import com.emmadev.bungalows.DTO.Laboral.LaboralPatch;
import com.emmadev.bungalows.DTO.Laboral.LaboralResponse;
import com.emmadev.bungalows.DTO.Laboral.AntiguedadesProximas;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Laboral;
import com.emmadev.bungalows.repository.LaboralRepository;
import com.emmadev.bungalows.utils.FechaUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LaboralService {

    private final LaboralRepository repository;
    private final UbicacionService ubicacionService;
    private final PuestoService puestoService;
    private final ColaboradorQueryService colaboradorQuery;

    public LaboralResponse save(ColaboradorRequestComplete dto, Colaborador colaborador){

        Laboral laboral = new Laboral();
        laboral.setFechaIngreso(dto.fechaIngreso());
        laboral.setSueldo(dto.sueldo());
        laboral.setTurno(dto.turno());
        laboral.setDiaDescanso(dto.diaDescanso());
        laboral.setEntrada(dto.entrada());
        laboral.setSalida(dto.salida());
        laboral.setColaborador(colaborador);
        laboral.setEstatus(dto.estatus());
        laboral.setUbicacion(
                ubicacionService.getUbicacionById(dto.idUbicacion())
        );

        laboral.setPuesto(
                puestoService.getById(dto.idPuesto())
        );

        repository.save(laboral);

        return getDto(laboral);
    }

    public void update(LaboralPatch patch){

        Colaborador colaborador = colaboradorQuery.getById(patch.colaboradorId());
        Laboral laboral = colaborador.getLaboral();

        if(patch.fechaIngreso() != null){
            laboral.setFechaIngreso(patch.fechaIngreso());
        }
        if(patch.sueldo()!= null){
            laboral.setSueldo(patch.sueldo());
        }
        if(patch.turno() != null){
            laboral.setTurno(patch.turno());
        }
        if(patch.diaDescanso()!=null){
            laboral.setDiaDescanso(patch.diaDescanso());
        }
        if(patch.entrada()!=null){
            laboral.setEntrada(patch.entrada());
        }
        if(patch.salida()!=null){
            laboral.setSalida(patch.salida());
        }
        if(patch.estatus() != null){
            laboral.setEstatus(patch.estatus());
        }
        if(patch.idUbicacion() != null){
            laboral.setUbicacion(
                    ubicacionService.getUbicacionById(patch.idUbicacion())
            );
        }
        if(patch.idPuesto() != null){
            laboral.setPuesto(
                    puestoService.getById(patch.idPuesto())
            );
        }

        repository.save(laboral);
    }

    @Transactional(readOnly = true)
    public List<AntiguedadesProximas> getAntiguedadesProximas() {
        return repository.findAll().stream()
                .map(this::toAntiguedadProxima)
                .flatMap(Optional::stream)
                .sorted(Comparator.comparingLong(AntiguedadesProximas::diasFaltantes))
                .toList();
    }


    @Transactional(readOnly = true)
    public List<Aniversarios> getAniversarios() {
        return repository.findAll().stream()
                .map(this::toAniversarios)
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<Aniversarios>  toAniversarios(Laboral laboral){
        long dias = FechaUtils.diasParaProximoAniversario(laboral.getFechaIngreso());
        int anios = FechaUtils.getAnios(laboral.getFechaIngreso());

        if(dias > 0 || anios==0) return  Optional.empty();

        return Optional.of(new Aniversarios(
                laboral.getId(),
                laboral.getColaborador().getNombreCompleto(),
                laboral.getPuesto().getNombre(),
                laboral.getUbicacion().getNombre(),
                laboral.getColaborador().getFotografia(),
                laboral.getFechaIngreso(),
                FechaUtils.getDiasVacaciones(anios),
                anios
        ));
    }

    private Optional<AntiguedadesProximas> toAntiguedadProxima(Laboral laboral) {
        long dias = FechaUtils.diasParaProximoAniversario(laboral.getFechaIngreso());
        if (dias > 60 || dias == 0) return Optional.empty();

        int anios = FechaUtils.getAnios(laboral.getFechaIngreso()) + 1;
        return Optional.of(new AntiguedadesProximas(
                laboral.getId(),
                laboral.getColaborador().getNombreCompleto(),
                laboral.getColaborador().getFotografia(),
                dias,
                anios,
                laboral.getFechaIngreso(),
                FechaUtils.getDiasVacaciones(anios)
        ));
    }

    public LaboralResponse getByColaborador(Long colaboradorId){
        Colaborador colaborador = colaboradorQuery.getById(colaboradorId);

        return getDto(colaborador.getLaboral());
    }

    protected LaboralResponse getDto(Laboral laboral){
        return new LaboralResponse(
                FechaUtils.getAnios(laboral.getFechaIngreso()),
                laboral.getFechaIngreso(),
                laboral.getSueldo(),
                laboral.getTurno().getNombre(),
                laboral.getDiaDescanso().getNombre(),
                laboral.getEntrada(),
                laboral.getSalida(),
                laboral.getEstatus().getNombre(),
                laboral.getPuesto().getNombre(),
                laboral.getUbicacion().getNombre()
        );
    }

}
