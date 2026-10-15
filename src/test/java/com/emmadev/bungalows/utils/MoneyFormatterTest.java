package com.emmadev.bungalows.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyFormatterTest {

    @Test
    @DisplayName("numero: formatea con dos decimales y símbolo $")
    void numero_formateaConDosDecimales() {
        assertThat(MoneyFormatter.numero(4300.0)).isEqualTo("$4,300.00");
        assertThat(MoneyFormatter.numero(1234.5)).isEqualTo("$1,234.50");
        assertThat(MoneyFormatter.numero(null)).isEqualTo("$0.00");
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "0,        Cero pesos 00/100 M.N.",
            "1,        Uno pesos 01/100 M.N.",
            "21,       Veintiuno pesos 00/100 M.N.",
            "100,      Cien pesos 00/100 M.N.",
            "101,      Ciento uno pesos 00/100 M.N.",
            "500,      Quinientos pesos 00/100 M.N.",
            "900,      Novecientos pesos 00/100 M.N.",
            "999,      Novecientos noventa y nueve pesos 00/100 M.N.",
            "1000,     Mil pesos 00/100 M.N.",
            "1500,     Mil quinientos pesos 00/100 M.N.",
            "21000,    Veintiún mil pesos 00/100 M.N.",
            "1000000,  Un millón pesos 00/100 M.N."
    })
    void letra_convierteMontosALetras(double monto, String esperado) {
        assertThat(MoneyFormatter.letra(monto)).isEqualTo(esperado);
    }

    @Test
    @DisplayName("letra: no revienta con montos cuyo resto de centenas es 90-99")
    void letra_noventaYAlgo_noRevienta() {
        // Este es el caso que provocaba IndexOutOfBounds con decenas[9]
        assertThat(MoneyFormatter.letra(95.0)).isEqualTo("Noventa y cinco pesos 00/100 M.N.");
        assertThat(MoneyFormatter.letra(199.99)).isEqualTo("Ciento noventa y nueve pesos 99/100 M.N.");
        assertThat(MoneyFormatter.letra(999.0)).isEqualTo("Novecientos noventa y nueve pesos 00/100 M.N.");
    }

    @Test
    @DisplayName("letra: monto null se trata como cero")
    void letra_null_esCero() {
        assertThat(MoneyFormatter.letra(null)).isEqualTo("Cero pesos 00/100 M.N.");
    }
}