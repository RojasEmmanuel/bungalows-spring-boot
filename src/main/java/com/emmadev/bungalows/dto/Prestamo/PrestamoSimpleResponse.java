package com.emmadev.bungalows.dto.Prestamo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrestamoSimpleResponse(
        Long id,
        BigDecimal monto,
        LocalDate fechaPrestamo,
        String estatus,
        String observaciones
) {
}
