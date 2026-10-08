package com.emmadev.bungalows.DTO.Ubicacion;

public record UbicacionResponse(
        Long id,
        String nombre,
        String slug,
        String direccion,
        String imagenPath
) {
}
