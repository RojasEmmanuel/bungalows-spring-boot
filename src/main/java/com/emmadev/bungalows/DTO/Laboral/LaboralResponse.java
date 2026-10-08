package com.emmadev.bungalows.DTO.Laboral;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record LaboralResponse(
        Integer antiguedad,
        LocalDate fechaIngreso,
        BigDecimal sueldo,
        String turno,
        String diaDescanso,
        LocalTime entrada,
        LocalTime salida,
        String estatus,
        String puesto,
        String ubicacion
) {
}
