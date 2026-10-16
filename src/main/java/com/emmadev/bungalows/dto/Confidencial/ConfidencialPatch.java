package com.emmadev.bungalows.dto.Confidencial;

import jakarta.validation.constraints.NotNull;

public record ConfidencialPatch(
        @NotNull(message = "Se requiere el id del colaborador")
        Long colaboradorId,
        String curp,
        String nss,
        String rfc
) {
}
