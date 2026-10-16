package com.emmadev.bungalows.dto.Nomina;

import java.math.BigDecimal;

public record PercepcionResponse(
        String concepto,
        BigDecimal monto
) {
}
