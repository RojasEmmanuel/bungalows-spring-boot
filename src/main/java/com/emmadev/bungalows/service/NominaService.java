package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Nomina.DeduccionRequest;
import com.emmadev.bungalows.DTO.Nomina.NominaPdfModel;
import com.emmadev.bungalows.DTO.Nomina.NominaRequest;
import com.emmadev.bungalows.DTO.Nomina.PercepcionRequest;
import com.emmadev.bungalows.Enums.TipoDeduccion;
import com.emmadev.bungalows.Enums.TipoDocumento;
import com.emmadev.bungalows.entity.*;
import com.emmadev.bungalows.repository.NominaRepository;
import com.emmadev.bungalows.service.Documento.DocumentoPdfService;
import com.emmadev.bungalows.service.Logos.LogoService;
import com.emmadev.bungalows.utils.DateFormatter;
import com.emmadev.bungalows.utils.MoneyFormatter;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Base64;
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


    @Transactional(readOnly = true)
    public byte[] generarPdfNomina(Long id) {
        Nomina nomina = repository.findWithPercepcionesById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe la nómina con ID: " + id
                        ));

        repository.findWithDeduccionesById(id);

        NominaPdfModel modelo = construirModelo(nomina);
        return documentoPdfService.generarPdf(TipoDocumento.NOMINA, modelo);
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
