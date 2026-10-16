package com.emmadev.bungalows.dto.Ubicacion;

public record UbicacionResponse(
        Long id,
        String nombre,
        String slug,
        String direccion,
        String imagenPath,
        Long empresaId
) {
}
