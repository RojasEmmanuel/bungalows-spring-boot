package com.emmadev.bungalows.dto.Laboral;

import java.time.LocalDate;

public record AntiguedadesProximas(
        Long id,
        String nombreColaborador,
        String fotografia,
        String puesto,
        String ubicacion,
        Long diasFaltantes,
        Integer antiguedadProxima,
        LocalDate fechaIngreso,
        Integer diasVacaciones
) {
}
