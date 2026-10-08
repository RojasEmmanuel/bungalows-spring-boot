package com.emmadev.bungalows.DTO.Direccion;

import com.emmadev.bungalows.Enums.Estados;
import jakarta.validation.constraints.NotNull;

public record DireccionPatch(
        @NotNull (message = "Se requiere el id del colaborador")
        Long colaboradorId,
        String calle,
        String colonia,
        String ciudad,
        Estados estado,
        String cp
) {
}
