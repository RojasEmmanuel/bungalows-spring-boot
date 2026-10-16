package com.emmadev.bungalows.dto.Empresa;

public record EmpresaResponse(
        Long id,
        String nombreLegal,
        String responsable,
        String regimenFiscal,
        String rfc,
        String direccion,
        String registroPatronal,
        String logoPath
) {
}
