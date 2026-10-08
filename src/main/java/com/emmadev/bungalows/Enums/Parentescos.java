package com.emmadev.bungalows.Enums;

public enum Parentescos {
    PADRE ("Padre"),
    MADRE ("Madre"),
    HIJO ("Hijo (a)"),
    HERMANO ("Hermano (a)"),
    CONYUGE ("Cónyuge");

    private final String nombre;

    Parentescos(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
}
