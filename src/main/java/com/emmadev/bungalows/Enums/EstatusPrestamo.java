package com.emmadev.bungalows.Enums;

public enum EstatusPrestamo {

    PRESTADO ("Prestado"),
    PAGADO ("Pagado"),
    PARCIAL("Parcial"),
    CANCELADO ("Cancelado");

    private final String nombre;

    EstatusPrestamo(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return  nombre;
    }
}
