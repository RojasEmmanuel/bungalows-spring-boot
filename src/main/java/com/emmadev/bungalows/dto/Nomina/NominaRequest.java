package com.emmadev.bungalows.dto.Nomina;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record NominaRequest(
        @NotNull(message = "Se requiere el id del laboral")
        Long laboralId,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Integer diasPago,
        @NotEmpty
        List<@Valid PercepcionRequest> percepciones,
        @NotEmpty
        List<@Valid DeduccionRequest> deducciones
) {
}
