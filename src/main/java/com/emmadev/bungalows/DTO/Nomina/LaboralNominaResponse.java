package com.emmadev.bungalows.DTO.Nomina;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LaboralNominaResponse(
        Long id,
        String nombreColaborador,
        String rfc,
        String curp,
        LocalDate fechaIngreso,
        String jornada,
        String nss,
        String puesto,
        String ubicacion,
        BigDecimal sueldo
) {
}
