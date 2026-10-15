package com.emmadev.bungalows.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {

    private DateFormatter(){
        throw  new UnsupportedOperationException("Esta clase no es instanciable");
    }

    public static String horario(LocalTime entrada, LocalTime salida){
        return entrada.toString()+ " - " + salida.toString();
    }

    public static String periodo(LocalDate inicio, LocalDate fin) {
        DateTimeFormatter diaMes = DateTimeFormatter.ofPattern(
                "dd 'de' MMMM", new Locale("es", "MX")
        );

        DateTimeFormatter fechaCompleta = DateTimeFormatter.ofPattern(
                "dd 'de' MMMM 'del' yyyy", new Locale("es", "MX")
        );

        if (inicio.getYear() == fin.getYear()) {
            return inicio.format(diaMes)
                    + " al "
                    + fin.format(diaMes)
                    + " del "
                    + fin.getYear();
        }

        return inicio.format(fechaCompleta)
                + " al "
                + fin.format(fechaCompleta);
    }

    // Formato largo legal: 12 de mayo del 2026
    public static String larga(LocalDate fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd 'de' MMMM 'del' yyyy", new Locale("es", "MX")
        );
        return fecha.format(formatter);
    }

    //Formato simple: 12/05/2026
    public static String simple(LocalDate fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd/MM/yyyy", new Locale("es", "MX")
        );
        return fecha.format(formatter);
    }

    // Formato extendido formal: 12 días del mes de mayo del año 2026
    public static String formal(LocalDate fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd 'días del mes de' MMMM 'del año' yyyy", new Locale("es", "MX")
        );
        return fecha.format(formatter);
    }

    // Mes y año: mayo 2026
    public static String mesAnio(LocalDate fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "MMMM yyyy", new Locale("es", "MX")
        );
        return fecha.format(formatter);
    }
}
