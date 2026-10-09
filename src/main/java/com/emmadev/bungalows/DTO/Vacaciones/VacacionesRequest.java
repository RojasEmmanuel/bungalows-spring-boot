package com.emmadev.bungalows.DTO.Vacaciones;

import com.emmadev.bungalows.Enums.EstatusVacaciones;

import java.time.LocalDate;

public record VacacionesRequest(
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Long colaboradorId,
        EstatusVacaciones estatusVacaciones
) {
}
