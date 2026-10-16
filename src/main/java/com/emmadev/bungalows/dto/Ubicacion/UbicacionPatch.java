package com.emmadev.bungalows.dto.Ubicacion;

public record UbicacionPatch(
        Long id,
        String nombre,
        String direccion,
        String imagenPath,
        Long empresaId
) {
}
