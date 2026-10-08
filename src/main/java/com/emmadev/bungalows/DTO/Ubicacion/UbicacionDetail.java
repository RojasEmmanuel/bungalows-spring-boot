package com.emmadev.bungalows.DTO.Ubicacion;

public record UbicacionDetail(
        Long id,
        String nombre,
        String slug,
        String direccion,
        String imagenPath,
        Long totalColaboradores,
        Long colaboradoresActivos
) {
}
