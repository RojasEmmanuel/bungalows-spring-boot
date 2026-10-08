package com.emmadev.bungalows.DTO.Colaborador;

import com.emmadev.bungalows.DTO.Laboral.LaboralResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record ColaboradorLaboralResponse(
        Long id,
        String nombreCompleto,
        int edad,
        String fotografia,
        Integer antiguedad,
        LocalDate fechaIngreso,
        BigDecimal sueldo,
        String turno,
        String diaDescanso,
        LocalTime entrada,
        LocalTime salida,
        String puesto,
        String ubicacion
) {
}
