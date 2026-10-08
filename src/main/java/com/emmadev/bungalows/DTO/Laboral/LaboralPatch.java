package com.emmadev.bungalows.DTO.Laboral;

import com.emmadev.bungalows.Enums.DiasLaborales;
import com.emmadev.bungalows.Enums.EstatusColaborador;
import com.emmadev.bungalows.Enums.Turnos;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record LaboralPatch(
        @NotNull (message = "Se requiere el id del colaborador")
        Long colaboradorId,

        @PastOrPresent(message = "La fecha no puede ser futura, es hoy o antes")
        LocalDate fechaIngreso,
        @Min(1000)
        BigDecimal sueldo,
        Turnos turno,
        DiasLaborales diaDescanso,
        LocalTime entrada,
        LocalTime salida,
        EstatusColaborador estatus,
        Long idPuesto,
        Long idUbicacion
) {
}
