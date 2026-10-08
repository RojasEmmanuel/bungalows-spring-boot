package com.emmadev.bungalows.Enums;

public enum EstatusVacaciones {
    AGENDADA("Agendada"),
    EN_CURSO("En curso"),
    FINALIZADA("Finalizada"),
    RECHAZADA("Rechazada");

    private final String nombre;
    EstatusVacaciones(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
}
