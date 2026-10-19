package com.emmadev.bungalows.service;

import com.emmadev.bungalows.Enums.EstatusVacaciones;
import com.emmadev.bungalows.Enums.TipoDocumento;
import com.emmadev.bungalows.dto.Vacaciones.*;
import com.emmadev.bungalows.entity.*;
import com.emmadev.bungalows.repository.VacacionesRespository;
import com.emmadev.bungalows.service.Documento.DocumentoPdfService;
import com.emmadev.bungalows.service.Logos.LogoService;
import com.emmadev.bungalows.utils.DateFormatter;
import com.emmadev.bungalows.utils.FechaUtils;
import com.emmadev.bungalows.utils.MoneyFormatter;
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
    private final LogoService logoService;
    private final DocumentoPdfService documentoPdfService;

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
        vacaciones.setDiasOcupados(diasOcupados);

        vacaciones.setEstatus(request.estatusVacaciones());

        if(request.estatusVacaciones().equals(EstatusVacaciones.PAGADA) && request.cantidadPagada() != null){
            vacaciones.setCantidadPagada(request.cantidadPagada());
        }

        //fecha de regreso un día despues.
        vacaciones.setFechaRegreso(request.fechaFin().plusDays(1));

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

        if(patch.estatusVacaciones() != null){
            vacaciones.setEstatus(patch.estatusVacaciones());
        }

        if(patch.estatusVacaciones().equals(EstatusVacaciones.PAGADA) && patch.cantidadPagada() != null){
            vacaciones.setCantidadPagada(patch.cantidadPagada());
        }

        respository.save(vacaciones);
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
                        vacaciones.getFechaRegreso(),
                        vacaciones.getEstatus().getNombre(),
                        vacaciones.getCantidadPagada()
                )
        ).toList();
    }


    @Transactional(readOnly = true)
    public VacacionesSImpleResponse getVacacciones(Long id){
        Vacaciones vacaciones = getById(id);
        return new VacacionesSImpleResponse(
                vacaciones.getId(),
                vacaciones.getFechaInicio(),
                vacaciones.getFechaFin(),
                vacaciones.getEstatus().getNombre(),
                vacaciones.getCantidadPagada()
        );
    }

    @Transactional
    public void eliminarVacaciones(Long id){
        respository.delete(getById(id));
    }


    @Transactional(readOnly = true)
    public byte[] generarDocumento(Long id){
        Vacaciones vacaciones = getById(id);


        if(vacaciones.getEstatus().equals(EstatusVacaciones.PAGADA)){
            VacacionesReciboModel modelo = construirModeloReciboPdf(vacaciones);
            return documentoPdfService.generarPdf(TipoDocumento.RECIBO_VACACIONES, modelo);
        }

        VacacionesConstanciaModel modelo = construirModeloConstanciaPdf(vacaciones);
       return  documentoPdfService.generarPdf(TipoDocumento.CONSTANCIA_VACACIONES, modelo);
    }

    private VacacionesConstanciaModel construirModeloConstanciaPdf(Vacaciones vacaciones){

        Empresa empresa = vacaciones.getLaboral().getUbicacion().getEmpresa();
        Colaborador colaborador = vacaciones.getLaboral().getColaborador();
        Laboral laboral = vacaciones.getLaboral();
        Vacacional vacacional = vacaciones.getPeriodoVacacional();

        return new VacacionesConstanciaModel(
                empresa.getNombreLegal().toUpperCase(),
                empresa.getRfc(),
                empresa.getRegimenFiscal().toUpperCase(),
                logoService.obtenerLogoBase64(empresa.getLogoPath()),
                empresa.getDireccion().toUpperCase(),
                DateFormatter.formal(LocalDate.now()).toUpperCase(),
                "CONSTANCIA DE VACACIONES",
                colaborador.getNombreCompleto().toUpperCase(),
                colaborador.getId().toString(),
                laboral.getUbicacion().getNombre().toUpperCase(),
                vacaciones.getDiasOcupados().toString(),
                DateFormatter.larga(vacaciones.getFechaInicio()).toUpperCase(),
                DateFormatter.larga(vacaciones.getFechaFin()).toUpperCase(),
                vacacional.getAnio().toString(),
                DateFormatter.larga(vacaciones.getFechaRegreso()).toUpperCase()
        );
    }

    private VacacionesReciboModel construirModeloReciboPdf(Vacaciones vacaciones){
        Empresa empresa = vacaciones.getLaboral().getUbicacion().getEmpresa();;
        Colaborador colaborador = vacaciones.getLaboral().getColaborador();

        return new VacacionesReciboModel(
                empresa.getNombreLegal().toUpperCase(),
                empresa.getRfc(),
                empresa.getRegimenFiscal().toUpperCase(),
                empresa.getDireccion().toUpperCase(),
                empresa.getResponsable().toUpperCase(),
                logoService.obtenerLogoBase64(empresa.getLogoPath()),
                DateFormatter.formal(LocalDate.now()).toUpperCase(),
                "RECIBO DE PAGO DE VACACIONES",
                colaborador.getNombreCompleto().toUpperCase(),
                colaborador.getId().toString(),
                MoneyFormatter.numero(vacaciones.getCantidadPagada().doubleValue()),
                MoneyFormatter.letra(vacaciones.getCantidadPagada().doubleValue()).toUpperCase()
        );
    }

    private Vacaciones getById(Long id){
        return respository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("No existe un registro de vacaciones con este id")
        );
    }
}
