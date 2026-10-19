package com.emmadev.bungalows.dto.Vacaciones;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VacacionesResponse(
        Long id,
        String nombreColaborador,
        String puesto,
        String ubicacion,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Integer diasOcupados,
        LocalDate fechaRegreso,
        String estatus,
        BigDecimal cantidadPagada
) {
}
