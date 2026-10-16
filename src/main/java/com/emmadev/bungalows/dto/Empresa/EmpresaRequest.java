package com.emmadev.bungalows.dto.Empresa;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record EmpresaRequest(
        @NotBlank(message = "Es necesario especificar el nombre de la empresa")
        String nombreLegal,

        @NotBlank(message = "Se requiere el nombre completo del responsable")
        @Length(min = 3, max = 200)
        String responsable,

        @NotBlank(message = "Se requiere el regimen fiscal")
        String regimenFiscal,

        String rfc,
        String direccion,
        String registroPatronal,
        String logoPath
) {
}
