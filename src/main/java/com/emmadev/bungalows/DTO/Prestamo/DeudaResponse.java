package com.emmadev.bungalows.DTO.Prestamo;

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
