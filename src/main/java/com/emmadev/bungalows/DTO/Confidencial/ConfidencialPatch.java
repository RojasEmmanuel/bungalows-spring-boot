package com.emmadev.bungalows.DTO.Confidencial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConfidencialPatch(
        @NotNull(message = "Se requiere el id del colaborador")
        Long colaboradorId,
        String curp,
        String nss,
        String rfc
) {
}
