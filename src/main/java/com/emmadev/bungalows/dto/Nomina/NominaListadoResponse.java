package com.emmadev.bungalows.dto.Nomina;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NominaListadoResponse(
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
        BigDecimal total

) {
}
