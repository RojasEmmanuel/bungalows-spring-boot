package com.emmadev.bungalows.dto.Empresa;

public record EmpresaPatch(
        Long id,
        String rfc,
        String direccion,
        String registroPatronal,
        String logoPath
) {
}
