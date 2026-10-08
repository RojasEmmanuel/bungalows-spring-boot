package com.emmadev.bungalows.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class FechaUtils {

    private FechaUtils(){
        throw  new UnsupportedOperationException("Esta clase no es instanciable");
    }

    public static int getAnios(LocalDate fechaInicial){

        if (fechaInicial == null) {
            throw new IllegalArgumentException("La fecha inicial no puede ser nula");
        }
        return Period.between(fechaInicial, LocalDate.now()).getYears();
    }

    public static int getDiasVacaciones(int anios) {

        return switch (anios) {
            case 0 -> 0;
            case 1 -> 12;
            case 2 -> 14;
            case 3 -> 16;
            case 4 -> 18;
            case 5 -> 20;
            case 6, 7, 8, 9, 10 -> 22;
            case 11, 12, 13, 14, 15 -> 24;
            case 16, 17, 18, 19, 20 -> 26;
            case 21, 22, 23, 24, 25 -> 28;
            case 26, 27, 28, 29, 30 -> 30;
            default -> 32;
        };
    }

    public static long diasParaProximoAniversario(LocalDate fecha) {
        LocalDate hoy = LocalDate.now();

        // Próximo aniversario en el año actual
        LocalDate proximo = fecha.withYear(hoy.getYear());

        // Si ya pasó (o es hoy), usar el año siguiente
        if (!proximo.isAfter(hoy)) {
            proximo = proximo.withYear(hoy.getYear() + 1);
        }

        return ChronoUnit.DAYS.between(hoy, proximo);
    }


}
