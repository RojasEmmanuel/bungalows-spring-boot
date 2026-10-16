package com.emmadev.bungalows.dto.Nomina;

import java.math.BigDecimal;

public record NominaResumenResponse(
        Long cantidadNominas,
        BigDecimal subtotal,
        BigDecimal descuentos,
        BigDecimal retenciones,
        BigDecimal total
) {
}
