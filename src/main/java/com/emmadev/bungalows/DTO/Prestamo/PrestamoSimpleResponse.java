package com.emmadev.bungalows.DTO.Prestamo;

import com.emmadev.bungalows.Enums.EstatusPrestamo;
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
