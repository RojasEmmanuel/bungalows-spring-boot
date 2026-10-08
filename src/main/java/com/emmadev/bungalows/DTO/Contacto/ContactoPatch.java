package com.emmadev.bungalows.DTO.Contacto;

import com.emmadev.bungalows.Enums.TipoContacto;
import com.emmadev.bungalows.validation.ContactoValido;
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
