package com.emmadev.bungalows.DTO.Empresa;

public record EmpresaPatch(
        Long id,
        String direccion,
        String registroPatronal,
        String logoPath
) {
}
