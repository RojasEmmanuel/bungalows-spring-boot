package com.emmadev.bungalows.DTO.Colaborador;

import com.emmadev.bungalows.Enums.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record ColaboradorRequestComplete(
        //datos Colaborador.
        @NotBlank(message = "Debe ingresar un nombre")
        @Size(min = 3, max = 100, message = "Nombre inválido")
        String nombre,
        @NotBlank(message = "Debe ingresar su apellido paterno")
        @Size(min = 3, max = 100, message = "Apellido inválido")
        String ap,
        @NotBlank(message = "Debe ingresar su apellido materno")
        @Size(min = 3, max = 100, message = "Apellido inválido")
        String am,
        @Past(message = "La fecha de nacimiento debe ser antes de hoy")
        LocalDate fechaNacimiento,
        EstadoCivil estadoCivil,
        String fotografia,

        // datos laborales
        @PastOrPresent(message = "La fecha no puede ser futura, es hoy o antes")
        LocalDate fechaIngreso,
        @Min(1000)
        BigDecimal sueldo,
        Turnos turno,
        DiasLaborales diaDescanso,
        LocalTime entrada,
        LocalTime salida,
        EstatusColaborador estatus,
        @NotNull
        Long idPuesto,
        @NotNull
        Long idUbicacion,

        // datos confidenciales
        @NotNull
        String curp,
        @NotNull
        String nss,
        @NotNull
        String rfc,

        // dirección
        @NotNull
        String calle,
        @NotNull
        String colonia,
        @NotNull
        String ciudad,
        @NotNull
        Estados estado,
        @NotNull
        String cp
) {
}
