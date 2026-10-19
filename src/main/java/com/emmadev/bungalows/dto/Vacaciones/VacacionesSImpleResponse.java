package com.emmadev.bungalows.dto.Vacaciones;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VacacionesSImpleResponse(
        Long id,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String estatusVacaciones,
        BigDecimal cantidadPagada
) {
}
