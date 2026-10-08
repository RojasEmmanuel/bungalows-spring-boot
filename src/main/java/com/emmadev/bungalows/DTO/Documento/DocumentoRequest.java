package com.emmadev.bungalows.DTO.Documento;

import jakarta.validation.constraints.NotNull;

public record DocumentoRequest(
        @NotNull(message = "Es necesario registrar el nombre")
        String nombre,
        @NotNull(message = "Es neceario definir la ruta del archivo")
        String path,
        @NotNull(message = "Se requiere el id del colaborador")
        Long colaboradorId
) {
}
