package com.emmadev.bungalows.DTO.Direccion;

import com.emmadev.bungalows.Enums.Estados;

public record DireccionResponse(
        String calle,
        String colonia,
        String ciudad,
        Estados estado,
        String cp
) {
}
