package com.emmadev.bungalows.service;

import com.emmadev.bungalows.dto.Nomina.*;
import com.emmadev.bungalows.Enums.TipoDeduccion;
import com.emmadev.bungalows.Enums.TipoDocumento;
import com.emmadev.bungalows.entity.*;
import com.emmadev.bungalows.repository.NominaRepository;
import com.emmadev.bungalows.service.Documento.DocumentoPdfService;
import com.emmadev.bungalows.service.Logos.LogoService;
import com.emmadev.bungalows.utils.DateFormatter;
import com.emmadev.bungalows.utils.MoneyFormatter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class NominaService {

    private final NominaRepository repository;
    private final LaboralService laboralService;
    private final DocumentoPdfService documentoPdfService;
    private final LogoService logoService;
    @Transactional
    public Long crearNomina(NominaRequest request){

        if(request.diasPago()==0){
            throw new IllegalArgumentException("El periodo de nomina debe tener al menos un día");
        }

        BigDecimal totalPercepciones = request.percepciones().stream()
                        .map(PercepcionRequest::monto)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal descuentos = request.deducciones().stream()
                .filter(d-> d.tipo()!=TipoDeduccion.FONDO_AHORRO)
                .map(DeduccionRequest::monto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal retenciones = request.deducciones().stream()
                .filter(d-> d.tipo()==TipoDeduccion.FONDO_AHORRO)
                .map(DeduccionRequest::monto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDeducciones = descuentos.add(retenciones);
        BigDecimal total = totalPercepciones.subtract(totalDeducciones);

        Nomina nomina = new Nomina();
        nomina.setFechaInicio(request.fechaInicio());
        nomina.setFechaFin(request.fechaFin());
        nomina.setDiasPago(request.diasPago());
        nomina.setTotal(total);
        nomina.setSubtotal(totalPercepciones);
        nomina.setDescuentos(descuentos);
        nomina.setRetenciones(retenciones);
        nomina.setLaboral(laboralService.getById(request.laboralId()));

        setDeduccionesToNomina(nomina, request.deducciones());
        setPercepcionesToNomina(nomina, request.percepciones());

        repository.save(nomina);
        return nomina.getId();
    }

    private void setDeduccionesToNomina(Nomina nomina, List<DeduccionRequest> deducciones){

        for(DeduccionRequest deduccionRequest: deducciones){
            Deduccion deduccion = new Deduccion();

            deduccion.setTipo(deduccionRequest.tipo());
            deduccion.setMonto(deduccionRequest.monto());

            nomina.addDeduccion(deduccion);
        }
    }

    private void setPercepcionesToNomina(Nomina nomina, List<PercepcionRequest> percepciones){

        for(PercepcionRequest percepcionRequest: percepciones){
            Percepcion percepcion = new Percepcion();

            percepcion.setConcepto(percepcionRequest.concepto());
            percepcion.setMonto(percepcionRequest.monto());

            nomina.addPercepcion(percepcion);
        }
    }


    public NominaResumenResponse getResumen(Integer ejercicio, LocalDate fechaInicio, LocalDate fechaFin) {
        if ((fechaInicio == null) != (fechaFin == null)) {
            throw new IllegalArgumentException(
                    "Debes proporcionar ambas fechas."
            );
        }

        if (fechaInicio != null && fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException(
                    "La fecha inicial no puede ser posterior a la final."
            );
        }

        if (fechaInicio != null && ejercicio != null) {
            throw new IllegalArgumentException(
                    "Utiliza el ejercicio o el rango de fechas, no ambos."
            );
        }

        if (ejercicio != null && ejercicio < 1900) {
            throw new IllegalArgumentException("Ejercicio inválido.");
        }

        return repository.obtenerResumen(
                ejercicio, fechaInicio, fechaFin
        );
    }


    // busca todas las nominas por medio de paginación.
    public Page<NominaListadoResponse> getNominas(
            Integer ejercicio,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            String colaborador,
            Pageable pageable
    ) {
        if ((fechaInicio == null) != (fechaFin == null)) {
            throw new IllegalArgumentException(
                    "Debes proporcionar ambas fechas."
            );
        }

        if (fechaInicio != null && fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException(
                    "La fecha inicial no puede ser posterior a la final."
            );
        }

        if (fechaInicio != null && ejercicio != null) {
            throw new IllegalArgumentException(
                    "Utiliza el ejercicio o el rango de fechas, no ambos."
            );
        }

        String nombre = colaborador == null || colaborador.isBlank()
                ? null
                : colaborador.trim();

        return repository.buscarNominas(
                ejercicio, fechaInicio, fechaFin, nombre, pageable
        );
    }


    // consulta los detalles de una nomina
    @Transactional(readOnly = true)
    public NominaDetalleResponse getDetalleNomina(Long id){

        return construirDetalleNomina(getById(id));
    }

    private NominaDetalleResponse construirDetalleNomina(Nomina n){

        var colaborador = n.getLaboral().getColaborador();

        return new NominaDetalleResponse(
                n.getId(),
                colaborador.getNombreCompleto(),
                n.getEjercicio(),
                DateFormatter.periodo(n.getFechaInicio(), n.getFechaFin()),
                n.getDiasPago(),
                n.getSubtotal(),
                n.getDescuentos(),
                n.getRetenciones(),
                n.getTotal(),

                n.getPercepciones().stream()
                        .map(
                        percepcion -> new PercepcionResponse(
                                percepcion.getConcepto(),
                                percepcion.getMonto()
                        )).toList(),

                n.getDeducciones().stream()
                        .map(
                            deduccion -> new DeduccionResponse(
                                    deduccion.getTipo().getNombre(),
                                    deduccion.getMonto()
                            )).toList()

        );
    }


    // consulta los ejercicios disponibles
    public List<Integer> obtenerEjercicios(){
        return repository.buscarEjerciciosDisponibles();
    }


    //consulta las nominas por colaborador
    @Transactional(readOnly = true)
    public Page<NominaListadoResponse> obtenerPorColaborador(Long colaboradorId, Pageable pageable){

        if(colaboradorId == null || colaboradorId < 0){
            throw  new IllegalArgumentException("El id del colaborador es inválido");
        }

        return repository.buscarPorColaborador(colaboradorId, pageable);
    }


    // GENERA EL PDF DE UNA NOMINA
    @Transactional(readOnly = true)
    public byte[] generarPdfNomina(Long id) {
        Nomina nomina = getById(id);

        repository.findWithDeduccionesById(id);

        NominaPdfModel modelo = construirModelo(nomina);
        return documentoPdfService.generarPdf(TipoDocumento.NOMINA, modelo);
    }

    private Nomina getById(Long id){
        return repository.findWithPercepcionesById(id)
            .orElseThrow(() -> new IllegalArgumentException("No existe la nómina con ID: " + id
        ));
    }

    private NominaPdfModel construirModelo(Nomina nomina) {
        Laboral laboral = nomina.getLaboral();
        Confidencial confidencial = laboral.getColaborador().getConfidencial();
        Ubicacion ubicacion = laboral.getUbicacion();
        Empresa empresa = ubicacion != null ? ubicacion.getEmpresa() : null;

        List<NominaPdfModel.Item> percepciones = nomina.getPercepciones().stream()
                .map(p -> new NominaPdfModel.Item(p.getConcepto().toUpperCase(),
                        MoneyFormatter.numero(p.getMonto().doubleValue())))
                .toList();

        List<NominaPdfModel.Item> deducciones = nomina.getDeducciones().stream()
                .map(d -> new NominaPdfModel.Item(d.getTipo().getNombre().toUpperCase(),
                        MoneyFormatter.numero(d.getMonto().doubleValue())))
                .toList();

        return new NominaPdfModel(
                empresa != null ? empresa.getNombreLegal().toUpperCase() : "",
                empresa != null ? empresa.getRegimenFiscal() : "",
                empresa != null ? empresa.getDireccion() : "",
                empresa != null ? empresa.getRegistroPatronal() : "",
                logoService.obtenerLogoBase64(empresa),
                empresa != null ? empresa.getRfc():"",

                laboral.getColaborador().getNombreCompleto().toUpperCase(),
                confidencial != null ? confidencial.getRfc() : "",
                confidencial != null ? confidencial.getCurp() : "",
                confidencial != null ? confidencial.getNss() : "",
                DateFormatter.larga(laboral.getFechaIngreso()).toUpperCase(),
                DateFormatter.horario(laboral.getEntrada(), laboral.getSalida()).toUpperCase(),
                laboral.getPuesto() != null ? laboral.getPuesto().getNombre().toUpperCase() : "",
                ubicacion != null ? ubicacion.getNombre().toUpperCase() : "",

                String.valueOf(nomina.getEjercicio()),
                DateFormatter.periodo(nomina.getFechaInicio(), nomina.getFechaFin()).toUpperCase(),
                String.valueOf(nomina.getDiasPago()),
                DateFormatter.simple(nomina.getFechaPago()),
                MoneyFormatter.numero(nomina.getSubtotal().doubleValue()),
                MoneyFormatter.numero(nomina.getDescuentos().doubleValue()),
                MoneyFormatter.numero(nomina.getRetenciones().doubleValue()),
                MoneyFormatter.numero(nomina.getTotal().doubleValue()),
                MoneyFormatter.letra(nomina.getTotal().doubleValue()).toUpperCase(),
                percepciones,
                deducciones
        );
    }
}
