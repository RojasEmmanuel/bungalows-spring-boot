package com.emmadev.bungalows.Enums;

public enum EstadoCivil {
    SOLTERO("Soltero"),
    CASADO ("Casado"),
    DIVORCIADO ("Divorciado"),
    VIUDO ("Viudo");

    private final String nombre;

    EstadoCivil(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
}
