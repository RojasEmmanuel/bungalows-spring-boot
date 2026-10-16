package com.emmadev.bungalows.dto.Prestamo;

import java.math.BigDecimal;

public record DeudaResponse(
        Long colaboradorId,
        String colaborador,
        String fotografia,
        String puesto,
        String ubicacion,
        Integer prestamosActivos,
        BigDecimal prestado,
        BigDecimal pagado,
        BigDecimal pendiente
) {
}
