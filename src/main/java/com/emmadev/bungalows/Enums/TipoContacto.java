package com.emmadev.bungalows.Enums;

public enum TipoContacto {
    TELEFONO ("Teléfono"),
    EMAIL ("Email");

    private final String nombre;

    TipoContacto(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
}
