package com.emmadev.bungalows.DTO.Responsable;

import com.emmadev.bungalows.Enums.Parentescos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ResponsableRequest(
        @NotBlank
        String nombre,

        @NotNull
        Parentescos parentesco,

        @Pattern(
            regexp = "\\d{10}",
            message = "El teléfono debe contener exactamente 10 dígitos"
        )
        String telefono,

        @NotNull(message = "Se requiere el id del colaborador")
        Long colaboradorId
) {
}
