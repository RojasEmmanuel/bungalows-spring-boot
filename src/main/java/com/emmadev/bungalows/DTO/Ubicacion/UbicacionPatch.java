package com.emmadev.bungalows.DTO.Ubicacion;

public record UbicacionPatch(
        Long id,
        String nombre,
        String direccion,
        String imagenPath,
        Long empresaId
) {
}
