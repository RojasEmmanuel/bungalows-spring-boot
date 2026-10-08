package com.emmadev.bungalows.Enums;

public enum EstatusColaborador {
    ACTIVO ("Activo"),
    INACTIVO ("Inactivo"),
    VACACIONES ("Vacaciones");

    private final String nombre;

    EstatusColaborador(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
}
