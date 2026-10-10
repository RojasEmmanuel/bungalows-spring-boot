package com.emmadev.bungalows.DTO.Prestamo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrestamoResponse(
        Long id,
        String nombreColaborador,
        String puestoColaborador,
        String ubicacionColaborador,
        BigDecimal monto,
        BigDecimal montoPagado,
        LocalDate fechaPrestamo,
        LocalDate fechaPago,
        String estatus,
        String observaciones
) {
}
