package com.emmadev.bungalows.service;

import com.emmadev.bungalows.DTO.Nomina.DeduccionRequest;
import com.emmadev.bungalows.DTO.Nomina.NominaRequest;
import com.emmadev.bungalows.DTO.Nomina.PercepcionRequest;
import com.emmadev.bungalows.Enums.TipoDeduccion;
import com.emmadev.bungalows.entity.Deduccion;
import com.emmadev.bungalows.entity.Nomina;
import com.emmadev.bungalows.entity.Percepcion;
import com.emmadev.bungalows.repository.NominaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class NominaService {

    private final NominaRepository repository;
    private final LaboralService laboralService;

    @Transactional
    public void crearNomina(NominaRequest request){

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
        nomina.setTotal(total);
        nomina.setSubtotal(totalPercepciones);
        nomina.setDescuentos(descuentos);
        nomina.setRetenciones(retenciones);
        nomina.setLaboral(laboralService.getById(request.laboralId()));

        setDeduccionesToNomina(nomina, request.deducciones());
        setPercepcionesToNomina(nomina, request.percepciones());

        repository.save(nomina);
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
}
