package com.emmadev.bungalows.Enums;

public enum TipoDeduccion {
    FONDO_AHORRO("Fondo de ahorro"),
    PRESTAMO_PERSONAL("Prestamo personal"),
    FALTANTES_CAJAS("Faltantes de cajas");

    private final String nombre;

    TipoDeduccion(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }
}
