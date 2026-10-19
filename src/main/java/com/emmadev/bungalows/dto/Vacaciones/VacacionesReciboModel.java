package com.emmadev.bungalows.dto.Vacaciones;

public record VacacionesReciboModel(

        String empresa,
        String empresaRfc,
        String empresaRegimenFiscal,
        String empresaDireccion,
        String empresaPropietario,
        String logoPath,

        //datos generales
        String fecha,
        String asunto,

        // datos del colaborador
        String colaborador,
        String numeroIdentificacion,
        String cantidad,
        String cantidadLetra
) {
}
