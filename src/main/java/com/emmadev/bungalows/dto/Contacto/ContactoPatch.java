package com.emmadev.bungalows.dto.Contacto;

import com.emmadev.bungalows.Enums.TipoContacto;
import jakarta.validation.constraints.NotNull;

public record ContactoPatch(
        @NotNull(message = "Es necesario el id del contacto")
        Long id,
        @NotNull
        TipoContacto tipoContacto,
        @NotNull
        String contacto
) {
}
