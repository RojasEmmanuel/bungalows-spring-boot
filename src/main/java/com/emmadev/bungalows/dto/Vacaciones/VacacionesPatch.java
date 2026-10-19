package com.emmadev.bungalows.dto.Vacaciones;

import com.emmadev.bungalows.Enums.EstatusVacaciones;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VacacionesPatch(
        @NotNull(message = "Se requiere el id de las vacaciones")
        Long id,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        EstatusVacaciones estatusVacaciones,
        BigDecimal cantidadPagada
) {
}
