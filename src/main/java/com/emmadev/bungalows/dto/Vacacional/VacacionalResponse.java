package com.emmadev.bungalows.dto.Vacacional;

public record VacacionalResponse(
        Long id,
        String colaboradorNombre,
        String colaboradorPuesto,
        String colaboradorUbicacion,
        Integer antiguedad,
        Integer diasVacaciones,
        Integer diasOcupados,
        Integer diasDisponibles
) {
}
