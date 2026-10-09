package com.emmadev.bungalows.DTO.Vacaciones;

import com.emmadev.bungalows.Enums.EstatusVacaciones;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record VacacionesPatch(
        @NotNull(message = "Se requiere el id de las vacaciones")
        Long id,
        @FutureOrPresent
        LocalDate fechaInicio,
        @Future
        LocalDate fechaFin,
        EstatusVacaciones estatusVacaciones
) {
}
