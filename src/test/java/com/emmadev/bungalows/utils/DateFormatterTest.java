package com.emmadev.bungalows.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateFormatterTest {

    @Test
    @DisplayName("letra: no revienta con la fecha actual")
    void letra_fecha_actual(){
        assertThat(DateFormatter.mesAnio(LocalDate.now())).isEqualTo("octubre 2026");
    }

    @Test
    @DisplayName("letra: periodos dentro del mismo mes")
    void letra_periodo_mismo_mes(){
        LocalDate inicio = LocalDate.of(2026,10,01);
        LocalDate fin = LocalDate.of(2026,10,15);
        assertThat(DateFormatter.periodo(inicio, fin)).isEqualTo("01 de octubre al 15 de octubre del 2026");
    }


    @Test
    @DisplayName("letra: periodos de diferentes meses")
    void letra_periodo_diferente_mes(){
        LocalDate inicio = LocalDate.of(2026,9,01);
        LocalDate fin = LocalDate.of(2026,10,15);
        assertThat(DateFormatter.periodo(inicio, fin)).isEqualTo("01 de septiembre al 15 de octubre del 2026");
    }

    @Test
    @DisplayName("letra: periodos de diferentes años")
    void letra_periodo_diferente_anios(){
        LocalDate inicio = LocalDate.of(2026,12,31);
        LocalDate fin = LocalDate.of(2027,01,15);

        assertThat(DateFormatter.periodo(inicio, fin))
                .isEqualTo("31 de diciembre del 2026 al 15 de enero del 2027");
    }

    @Test
    @DisplayName("letra: formato formal")
    void letra_formato_formal(){
        LocalDate fecha = LocalDate.of(2026,10,15);

        assertThat(DateFormatter.formal(fecha)).isEqualTo("15 días del mes de octubre del año 2026");
    }
}