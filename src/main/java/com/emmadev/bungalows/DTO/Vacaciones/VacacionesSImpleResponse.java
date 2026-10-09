package com.emmadev.bungalows.DTO.Vacaciones;

import java.time.LocalDate;

public record VacacionesSImpleResponse(
        Long id,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String estatusVacaciones
) {
}
