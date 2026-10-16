package com.emmadev.bungalows.dto.Direccion;

import com.emmadev.bungalows.Enums.Estados;

public record DireccionResponse(
        String calle,
        String colonia,
        String ciudad,
        Estados estado,
        String cp
) {
}
