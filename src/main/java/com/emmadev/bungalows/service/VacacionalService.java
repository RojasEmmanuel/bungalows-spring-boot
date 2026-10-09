package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Vacacional.VacacionalResponse;
import com.emmadev.bungalows.Enums.VacacionalEstatus;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Laboral;
import com.emmadev.bungalows.entity.Vacacional;
import com.emmadev.bungalows.repository.VacacionalRepository;
import com.emmadev.bungalows.utils.FechaUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class VacacionalService {

    private VacacionalRepository repository;

    // creamos el periodo vacacional se ejcuta de forma automatica.
    public void crearPeriodoVacacional(Laboral laboral){

        int anioActual = LocalDate.now().getYear();

        vencerUltimoVacacional(laboral);
        if (repository.existsByColaboradorIdAndAnio(
                laboral.getColaborador().getId(), anioActual)) {
            return;
        }
        Vacacional vacacional = new Vacacional();
        vacacional.setAntiguedad(FechaUtils.getAnios(laboral.getFechaIngreso()));
        vacacional.setColaborador(laboral.getColaborador());
        vacacional.setDiasOcupados(0);
        vacacional.setDiasVacaciones(FechaUtils.getDiasVacaciones(vacacional.getAntiguedad()));
        vacacional.setDiasDisponibles(vacacional.getDiasVacaciones());
        vacacional.setAnio(anioActual);

        repository.save(vacacional);
    }

    public void vencerUltimoVacacional(Laboral laboral){
        Vacacional vacacional = repository.findByColaboradorAndEstatus(
                laboral.getColaborador(),
                VacacionalEstatus.DISPONIBLE
        );

        if(vacacional != null){ // si tiene vacional anterior se vence
            vacacional.setEstatus(VacacionalEstatus.VENCIDA);
            repository.save(vacacional);
        }
    }

    //recibe los dias ocupados por vacaciones y actualizar los dias disponibles.
    public void actualizarVacacional(int diasOcupados, Vacacional vacacional){
        vacacional.setDiasDisponibles(vacacional.getDiasDisponibles()-diasOcupados);
        vacacional.setDiasOcupados(vacacional.getDiasOcupados() + diasOcupados);

        if(vacacional.getDiasDisponibles() == 0){
            vacacional.setEstatus(VacacionalEstatus.VENCIDA);
        }

        repository.save(vacacional);
    }

    public Vacacional getVacacional(Colaborador colaborador){

        return repository.findByColaboradorAndEstatus(
                colaborador,
                VacacionalEstatus.DISPONIBLE
        );
    }

    //obtiene los periodos vacacionales activos.
    public List<VacacionalResponse> getPeriodosVacacionalesActivos(){

        return repository.findByEstatus(VacacionalEstatus.DISPONIBLE).stream().map(
                vacacional -> new VacacionalResponse(
                        vacacional.getId(),
                        vacacional.getColaborador().getNombreCompleto(),
                        vacacional.getColaborador().getLaboral().getPuesto().getNombre(),
                        vacacional.getColaborador().getLaboral().getUbicacion().getNombre(),
                        vacacional.getAntiguedad(),
                        vacacional.getDiasVacaciones(),
                        vacacional.getDiasOcupados(),
                        vacacional.getDiasDisponibles()
                )
        ).toList();
    }
}
