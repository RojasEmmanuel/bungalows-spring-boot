package com.emmadev.bungalows.DTO.Responsable;

import com.emmadev.bungalows.Enums.Parentescos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ResponsablePatch(
        @NotNull(message = "Se requiere el id del contacto responsable")
        Long id,
        String nombre,
        Parentescos parentesco,

        @Pattern(
                regexp = "\\d{10}",
                message = "El teléfono debe contener exactamente 10 dígitos"
        )
        String telefono
) {
}
