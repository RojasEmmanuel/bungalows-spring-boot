package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Vacaciones.VacacionesRequest;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Vacacional;
import com.emmadev.bungalows.entity.Vacaciones;
import com.emmadev.bungalows.repository.VacacionesRespository;
import com.emmadev.bungalows.utils.FechaUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VacacionesService {

    private final VacacionalService vacacionalService;
    private final ColaboradorQueryService queryService;
    private final VacacionesRespository respository;

    @Transactional
    public void registrarVacaciones(VacacionesRequest request){

        Colaborador colaborador = queryService.getById(request.colaboradorId());
        Vacacional vacacional = vacacionalService.getVacacional(colaborador);

        if(vacacional == null){
            throw  new IllegalArgumentException(
                    "Este colaborador no cuenta con un periodo vacacional vigente"
            );
        }

        int diasOcupados = FechaUtils.getIntervaloFechas(request.fechaInicio(), request.fechaFin());

        if(diasOcupados > vacacional.getDiasDisponibles()){
            throw  new IllegalArgumentException(
                    "Los días ocupados superan el total disponible de este periodo"
            );
        }

        Vacaciones vacaciones = new Vacaciones();
        vacaciones.setPeriodoVacacional(vacacional);
        vacaciones.setFechaInicio(request.fechaInicio());
        vacaciones.setFechaFin(request.fechaFin());
        vacaciones.setEstatus(request.estatusVacaciones());
        vacacionalService.actualizarVacacional(diasOcupados, vacacional);

        respository.save(vacaciones);
    }
}
