package com.emmadev.bungalows.DTO.Colaborador;

import java.time.LocalDate;

// record para listar todos los colaboradores.
public record ColaboradorResponse(
        Long id,
        String nombre,
        String ap,
        String am,
        LocalDate fechaNacimiento,
        String estadoCivil,
        String fotografia
) {
}
