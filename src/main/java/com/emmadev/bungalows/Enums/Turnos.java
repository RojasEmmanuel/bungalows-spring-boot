package com.emmadev.bungalows.Enums;

public enum Turnos {
    MATUTINO ("Matutino"),
    VESPERTINO ("Vespertino"),
    NOCTURNO("Nocturno");

    private final String nombre;

    Turnos(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
}
