package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Vacaciones.VacacionesPatch;
import com.emmadev.bungalows.DTO.Vacaciones.VacacionesRequest;
import com.emmadev.bungalows.DTO.Vacaciones.VacacionesResponse;
import com.emmadev.bungalows.DTO.Vacaciones.VacacionesSImpleResponse;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Vacacional;
import com.emmadev.bungalows.entity.Vacaciones;
import com.emmadev.bungalows.repository.VacacionesRespository;
import com.emmadev.bungalows.utils.FechaUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
        vacaciones.setDiasOcupados(diasOcupados);
        vacaciones.setLaboral(colaborador.getLaboral());
        vacacionalService.actualizarVacacional(diasOcupados, vacacional);

        respository.save(vacaciones);
    }

    @Transactional
    public void editarVacaciones(VacacionesPatch patch){
        Vacaciones vacaciones = getById(patch.id());

        if(patch.fechaInicio() != vacaciones.getFechaInicio() || patch.fechaFin() != vacaciones.getFechaFin()){
            int intervalo = FechaUtils.getIntervaloFechas(patch.fechaInicio(), patch.fechaFin());
            int disponible = vacaciones.getDiasOcupados() + vacaciones.getPeriodoVacacional().getDiasDisponibles();
            int diasOcupadosVacacional = vacaciones.getPeriodoVacacional().getDiasOcupados();

            if(intervalo<=disponible){
                vacaciones.getPeriodoVacacional().setDiasOcupados(diasOcupadosVacacional - vacaciones.getDiasOcupados());

                vacaciones.setDiasOcupados(intervalo);
                vacaciones.getPeriodoVacacional().setDiasDisponibles(disponible-intervalo);
                vacaciones.getPeriodoVacacional().setDiasOcupados(
                        vacaciones.getPeriodoVacacional().getDiasOcupados() + intervalo
                );
            }

        }
    }

    @Transactional(readOnly = true)
    public List<VacacionesResponse> listarVacaciones(){
        return respository.findAll().stream().map(
                vacaciones -> new VacacionesResponse(
                        vacaciones.getId(),
                        vacaciones.getLaboral().getColaborador().getNombreCompleto(),
                        vacaciones.getLaboral().getPuesto().getNombre(),
                        vacaciones.getLaboral().getUbicacion().getNombre(),
                        vacaciones.getFechaInicio(),
                        vacaciones.getFechaFin(),
                        vacaciones.getDiasOcupados(),
                        vacaciones.getEstatus().getNombre()
                )
        ).toList();
    }


    @Transactional(readOnly = true)
    public VacacionesSImpleResponse getVacacciones(Long id){
        Vacaciones vacaciones = getById(id);
        return new VacacionesSImpleResponse(
                vacaciones.getId(),
                vacaciones.getFechaInicio(),
                vacaciones.getFechaFin()
        );
    }

    @Transactional
    public void eliminarVacaciones(Long id){
        respository.delete(getById(id));
    }

    private Vacaciones getById(Long id){
        return respository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("No existe un registro de vacaciones con este id")
        );
    }
}
