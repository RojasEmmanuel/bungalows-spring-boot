package com.emmadev.bungalows.DTO.Vacacional;

import com.emmadev.bungalows.entity.Colaborador;

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
