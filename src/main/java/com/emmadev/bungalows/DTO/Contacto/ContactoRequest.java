package com.emmadev.bungalows.DTO.Contacto;

import com.emmadev.bungalows.Enums.TipoContacto;
import com.emmadev.bungalows.validation.ContactoValido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ContactoValido
public record ContactoRequest(
        @NotNull(message = "Es necesario el id del colaborador")
        Long colaboradorId,

        @NotNull(message = "El tipo de contacto es obligatorio")
        TipoContacto tipoContacto,

        @NotBlank(message = "El valor del contacto es obligatorio")
        String contacto
) {
}
