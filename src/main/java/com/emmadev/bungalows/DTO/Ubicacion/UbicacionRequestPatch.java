package com.emmadev.bungalows.DTO.Ubicacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UbicacionRequestPatch(

        @NotNull(message = "Debe enviar id")
        Long id,

        @NotBlank(message = "Debe ingresar un nombre")
        @Size(
                min = 3, max = 50,
                message = "El nombre debe tener minimo 3 caracteres y maximo 50 caracteres"
        )
        String nombre,

        @NotBlank(message = "Debe ingresar una dirección")
        String direccion,

        String imagenPath

) {
}
