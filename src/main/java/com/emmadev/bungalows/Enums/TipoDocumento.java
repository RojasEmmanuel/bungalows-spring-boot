package com.emmadev.bungalows.Enums;

public enum TipoDocumento {
    NOMINA("nomina"),
    CONSTANCIA_VACACIONES("constancia_vacaciones"),
    RECIBO_VACACIONES("recibo_vacaciones");


    private final String plantilla;
    TipoDocumento(String plantilla){
        this.plantilla = plantilla;
    }

    public String getPlantilla(){
        return this.plantilla;
    }
}
