package com.emmadev.bungalows.DTO.Prestamo;

import com.emmadev.bungalows.Enums.EstatusPrestamo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PrestamoPatch(
        @NotNull(message = "Se requiere el id del prestamo")
        Long id,
        @DecimalMin(value = "1.0", message = "cuando menos debe prestar $1.0")
        BigDecimal monto,
        @PastOrPresent(message = "la fecha no puede ser futura")
        LocalDate fechaPrestamo,
        EstatusPrestamo estatus,
        String observaciones
) {
}
