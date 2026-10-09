package com.emmadev.bungalows.DTO.Laboral;

import java.time.LocalDate;

public record Aniversarios(
        Long id,
        String nombreColaborador,
        String puesto,
        String ubicacion,
        String fotografia,
        LocalDate fechaIngreso,
        Integer diasVacaciones,
        Integer antiguedad

) {
}
