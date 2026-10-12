package com.emmadev.bungalows.DTO.Empresa;

public record EmpresaResponse(
        Long id,
        String nombreLegal,
        String responsable,
        String registroFiscal,
        String direccion,
        String registroPatronal,
        String logoPath
) {
}
