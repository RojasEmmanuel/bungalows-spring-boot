package com.emmadev.bungalows.DTO.Prestamo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrestamoRequest(
        @DecimalMin(value = "1.0", message = "cuando menos debe prestar $1.0")
        BigDecimal monto,
        @PastOrPresent(message = "la fecha no puede ser futura")
        LocalDate fechaPrestamo,
        String observaciones,
        @NotNull(message = "Se requiere el id el colaborador")
        Long colaboradorId
) {
}
