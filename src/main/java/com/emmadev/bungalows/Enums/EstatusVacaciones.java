package com.emmadev.bungalows.Enums;

public enum EstatusVacaciones {
    AGENDADA("Agendada"),
    PAGADA("Pagada");

    private final String nombre;
    EstatusVacaciones(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
}
