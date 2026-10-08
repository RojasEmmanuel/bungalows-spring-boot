package com.emmadev.bungalows.DTO.Laboral;

import java.time.LocalDate;

public record AntiguedadesProximas(
        Long id,
        String nombreColaborador,
        Long diasFaltantes,
        Integer antiguedadProxima,
        LocalDate fechaIngreso,
        Integer diasVacaciones
) {
}
