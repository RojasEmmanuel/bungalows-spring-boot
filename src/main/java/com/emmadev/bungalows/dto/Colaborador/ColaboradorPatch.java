package com.emmadev.bungalows.dto.Colaborador;

import com.emmadev.bungalows.Enums.EstadoCivil;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

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
