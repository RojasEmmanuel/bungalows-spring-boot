package com.emmadev.bungalows.DTO.Nomina;

import java.util.List;

public record NominaPdfModel(
        // Empresa
        String empresaNombreLegal,
        String empresaRegimenFiscal,
        String empresaDireccion,
        String empresaRegistroPatronal,
        String empresaLogoBase64,   // data:image/png;base64,...
        String empresaRfc,

        // Empleado
        String empleadoNombre,
        String empleadoRfc,
        String empleadoCurp,
        String empleadoNss,
        String empleadoFechaIngreso,
        String empleadoJornada,
        String empleadoPuesto,
        String empleadoDepartamento,

        // Nómina
        String ejercicio,
        String periodo,
        String diasPagados,
        String fechaPago,
        String subtotal,
        String descuentos,
        String retenciones,
        String total,
        String totalLetra,

        // Tablas
        List<Item> percepciones,
        List<Item> deducciones
) {
    public record Item(String concepto, String monto) {}
}