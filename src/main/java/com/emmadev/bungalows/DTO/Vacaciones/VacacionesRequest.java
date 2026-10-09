package com.emmadev.bungalows.DTO.Vacaciones;

import com.emmadev.bungalows.Enums.EstatusVacaciones;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record VacacionesRequest(
        @FutureOrPresent
        LocalDate fechaInicio,
        @Future
        LocalDate fechaFin,
        @NotNull(message = "Se requiere el id del colaborador")
        Long colaboradorId,
        EstatusVacaciones estatusVacaciones
) {
}
