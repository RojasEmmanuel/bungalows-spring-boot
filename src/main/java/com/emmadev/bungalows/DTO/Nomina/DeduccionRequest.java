package com.emmadev.bungalows.DTO.Nomina;

import com.emmadev.bungalows.Enums.TipoDeduccion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record DeduccionRequest(
        @NotNull
        TipoDeduccion tipo,
        @NotNull
        @PositiveOrZero
        BigDecimal monto
) {
}
