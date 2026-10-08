package com.emmadev.bungalows.DTO.Colaborador;

import com.emmadev.bungalows.Enums.DiasLaborales;
import com.emmadev.bungalows.Enums.EstadoCivil;
import com.emmadev.bungalows.Enums.EstatusColaborador;
import com.emmadev.bungalows.Enums.Turnos;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record ColaboradorPatch(
        @NotNull(message = "El id del colaborador es requerido")
        Long id,
        String nombre,
        String ap,
        String am,
        @Past(message = "La fecha de nacimiento debe ser antes de hoy")
        LocalDate fechaNacimiento,
        EstadoCivil estadoCivil,
        String fotografia
        ) {
}
