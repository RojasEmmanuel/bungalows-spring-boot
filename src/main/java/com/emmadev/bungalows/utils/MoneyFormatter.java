package com.emmadev.bungalows.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyFormatter {

    private static final Locale LOCALE_ES_MX = new Locale("es", "MX");

    private MoneyFormatter(){
        throw new UnsupportedOperationException("Esta clase no es instanciable");
    }

    public static String numero(Double monto) {
        double valor = (monto == null) ? 0.0 : monto;
        NumberFormat formatter = NumberFormat.getNumberInstance(LOCALE_ES_MX);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return "$" + formatter.format(valor);
    }

    public static String letra(Double monto) {
        double valor = (monto == null) ? 0.0 : monto;

        double enteroDouble = Math.floor(valor);
        long entero = (long) enteroDouble;
        int decimales = (int) Math.round((valor - enteroDouble) * 100);

        String enteroEnLetras = numeroALetras(entero);

        return capitalizar(enteroEnLetras)
                + " pesos "
                + String.format("%02d", decimales)
                + "/100 M.N.";
    }

    //  Helpers

    private static String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) return texto;
        return Character.toUpperCase(texto.charAt(0)) + texto.substring(1);
    }

    private static String numeroALetras(long numero) {
        if (numero == 0) return "cero";
        if (numero < 0) return "menos " + numeroALetras(-numero);

        if (numero < 1000) return centenasALetras((int) numero);
        if (numero < 1_000_000) {
            long miles = numero / 1000;
            long resto = numero % 1000;
            String prefijo = (miles == 1) ? "mil" : centenasALetras((int) miles) + " mil";
            return resto == 0 ? prefijo : prefijo + " " + centenasALetras((int) resto);
        }
        if (numero < 1_000_000_000L) {
            long millones = numero / 1_000_000;
            long resto = numero % 1_000_000;
            String prefijo = (millones == 1)
                    ? "un millón"
                    : numeroALetras(millones) + " millones";
            return resto == 0 ? prefijo : prefijo + " " + numeroALetras(resto);
        }
        // Para montos aún mayores puedes extender a "mil millones", etc.
        throw new IllegalArgumentException("Monto demasiado grande: " + numero);
    }

    private static String centenasALetras(int numero) {
        String[] unidades = {
                "", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete",
                "ocho", "nueve", "diez", "once", "doce", "trece", "catorce",
                "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve",
                "veinte", "veintiuno", "veintidós", "veintitrés", "veinticuatro",
                "veinticinco", "veintiséis", "veintisiete", "veintiocho", "veintinueve"
        };
        String[] decenas = {
                "", "", "", "treinta", "cuarenta", "cincuenta",
                "sesenta", "setenta", "ochenta", "noventa"
        };
        String[] centenas = {
                "", "ciento", "doscientos", "trescientos", "cuatrocientos",
                "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"
        };

        if (numero == 0) return "";
        if (numero == 100) return "cien";

        String resultado = "";

        int c = numero / 100;
        int resto = numero % 100;

        if (c > 0) resultado += centenas[c];

        if (resto > 0) {
            if (!resultado.isEmpty()) resultado += " ";

            if (resto < 30) {
                resultado += unidades[resto];
            } else {
                int d = resto / 10;
                int u = resto % 10;
                resultado += decenas[d];
                if (u > 0) resultado += " y " + unidades[u];
            }
        }

        if (resultado.equals("uno")) resultado = "un";

        return resultado;
    }
}