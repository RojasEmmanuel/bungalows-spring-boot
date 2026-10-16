package com.emmadev.bungalows.dto.Nomina;

import java.math.BigDecimal;

public record DeduccionResponse(
        String tipo,
        BigDecimal monto
) {
}
