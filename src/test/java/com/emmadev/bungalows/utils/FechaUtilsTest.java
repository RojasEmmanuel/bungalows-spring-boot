package com.emmadev.bungalows.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FechaUtilsTest {

    @Test
    @DisplayName("Debe retornar el intervalo cerrado de fechas")
    void debeRetornarElIntervaloCerradoDeFechas(){

        LocalDate inicio = LocalDate.of(2026,10,18);
        LocalDate fin = LocalDate.of(2026, 10, 25);

        int intervalo = FechaUtils.getIntervaloFechas(inicio, fin);

        assertEquals(intervalo, 8);
    }

    @Test
    @DisplayName("Debe retornar los días exactos para un aniversario proximo")
    void debeRetornarElNumeroDeDiasParaProximoAniversario(){
        LocalDate fecha = LocalDate.of(2026, 10,25);
        Long diasProximos =  FechaUtils.diasParaProximoAniversario(fecha);
        assertEquals(diasProximos, 7L);
    }

    @Test
    @DisplayName("Si hoy es el aniversario debe retornar 0")
    void retornaCeroSiEsAniversario(){
        LocalDate fecha = LocalDate.of(2026, 10,18);
        Long diasProximos =  FechaUtils.diasParaProximoAniversario(fecha);
        assertEquals(diasProximos, 0L);
    }


    @Test
    @DisplayName("Debe retornar el total de dias de vacacciones correctamente")
    void retornaTotalDiasVacacionesDeAcuerdoALaLeyConNumerosPositivos(){
        int vacaciones = FechaUtils.getDiasVacaciones(5);
        assertEquals(vacaciones, 20);
    }

    @Test
    @DisplayName("Debe lanzar una excepcion cuando la antiguedad es un numero negativo")
    void lanzaExcepcionEnAntiguedadesNegativas(){

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                ()->FechaUtils.getDiasVacaciones(-5)
        );

        assertEquals(
                "No puede haber antiguedades negativas",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("NO debe permitir fechas nulas al consultar los años")
    void lanzaErrorFechaNula(){

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                ()->FechaUtils.getAnios(null)
        );

        assertEquals("La fecha inicial no puede ser nula", exception.getMessage());
    }
}