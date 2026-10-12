package com.emmadev.bungalows.DTO.Nomina;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NominaRequest(
        @NotNull(message = "Se requiere el id del laboral")
        Long laboralId,
        @NotBlank(message = "Se requiere la fecha de inicio")
        LocalDate fechaInicio,
        @NotBlank(message = "Se requiere la fecha de fin del periodo")
        LocalDate fechaFin,
        Integer diasPago,
        BigDecimal subtotal,
        BigDecimal descuentos,
        BigDecimal retenciones,
        BigDecimal total
) {
}
