package com.emmadev.bungalows.dto.Vacaciones;

public record VacacionesConstanciaModel(
        // datos empresa
        String empresa,
        String rfc,
        String regimenFiscal,
        String logoPath,

        //datos generales
        String lugar,
        String fecha,
        String asunto,
        String colaborador,
        String numeroIdentificacion,
        String area,

        // detalles de las vacaciones
        String numeroDias,
        String fechaInicio,
        String fechaFin,
        String anioPeriodoVacacional,
        String fechaRegreso
) {
}
