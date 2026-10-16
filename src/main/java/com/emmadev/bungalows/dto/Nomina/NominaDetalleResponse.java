package com.emmadev.bungalows.dto.Nomina;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record NominaDetalleResponse(
        Long id,
        String nombreColaborador,
        Integer ejercicio,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        LocalDate fechaPago,
        Integer diasPago,
        BigDecimal subtotal,
        BigDecimal descuentos,
        BigDecimal retenciones,
        BigDecimal total,
        List<PercepcionResponse> percepciones,
        List<DeduccionResponse> deducciones
) {}