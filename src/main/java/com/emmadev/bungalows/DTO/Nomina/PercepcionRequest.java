package com.emmadev.bungalows.DTO.Nomina;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record PercepcionRequest(
        @NotBlank
        String concepto,
        @NotNull
        @PositiveOrZero
        BigDecimal monto
) {
}
