package com.emmadev.bungalows.dto.Puesto;

import jakarta.validation.constraints.NotBlank;

public record PuestoRequest(
        @NotBlank
        String nombre,
        @NotBlank
        String descripcion
) {
}
