package com.emmadev.bungalows.DTO.Vacaciones;

import java.time.LocalDate;

public record VacacionesResponse(
        Long id,
        String nombreColaborador,
        String puesto,
        String ubicacion,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Integer diasOcupados,
        String estatus
) {
}
