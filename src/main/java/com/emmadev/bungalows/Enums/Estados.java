package com.emmadev.bungalows.Enums;

public enum Estados {
    AGUASCALIENTES("Aguascalientes"),
    BAJA_CALIFORNIA("Baja California"),
    BAJA_CALIFORNIA_SUR("Baja California Sur"),
    CAMPECHE("Campeche"),
    CHIAPAS("Chiapas"),
    CHIHUAHUA("Chihuahua"),
    CIUDAD_DE_MEXICO("Ciudad de México"),
    COAHUILA("Coahuila"),
    COLIMA("Colima"),
    DURANGO("Durango"),
    GUANAJUATO("Guanajuato"),
    GUERRERO("Guerrero"),
    HIDALGO("Hidalgo"),
    JALISCO("Jalisco"),
    ESTADO_DE_MEXICO("Estado de México"),
    MICHOACAN("Michoacán"),
    MORELOS("Morelos"),
    NAYARIT("Nayarit"),
    NUEVO_LEON("Nuevo León"),
    OAXACA("Oaxaca"),
    PUEBLA("Puebla"),
    QUERETARO("Querétaro"),
    QUINTANA_ROO("Quintana Roo"),
    SAN_LUIS_POTOSI("San Luis Potosí"),
    SINALOA("Sinaloa"),
    SONORA("Sonora"),
    TABASCO("Tabasco"),
    TAMAULIPAS("Tamaulipas"),
    TLAXCALA("Tlaxcala"),
    VERACRUZ("Veracruz"),
    YUCATAN("Yucatán"),
    ZACATECAS("Zacatecas");

    private final String nombre;

    Estados(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}