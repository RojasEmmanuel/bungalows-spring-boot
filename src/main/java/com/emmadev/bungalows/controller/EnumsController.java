package com.emmadev.bungalows.controller;

import com.emmadev.bungalows.Enums.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enums")
public class EnumsController {

    /** Enums simples: nombre = value del enum, label = texto legible */
    public record EnumOption(String value, String label) {}

    @GetMapping("/estados")
    public List<EnumOption> estados() {
        return Arrays.stream(Estados.values())
                .map(e -> new EnumOption(e.name(), e.getNombre()))
                .toList();
    }

    @GetMapping("/estados-civil")
    public List<EnumOption> estadosCivil() {
        return Arrays.stream(EstadoCivil.values())
                .map(e -> new EnumOption(e.name(), e.getNombre()))
                .toList();
    }

    @GetMapping("/turnos")
    public List<EnumOption> turnos() {
        return Arrays.stream(Turnos.values())
                .map(e -> new EnumOption(e.name(), e.getNombre()))
                .toList();
    }

    @GetMapping("/dias-laborales")
    public List<EnumOption> diasLaborales() {
        return Arrays.stream(DiasLaborales.values())
                .map(e -> new EnumOption(e.name(), e.getNombre()))
                .toList();
    }

    @GetMapping("/estatus-colaborador")
    public List<EnumOption> estatusColaborador() {
        return Arrays.stream(EstatusColaborador.values())
                .map(e -> new EnumOption(e.name(), e.getNombre()))
                .toList();
    }

    @GetMapping("/tipos-contacto")
    public List<EnumOption> tiposContactos(){
        return Arrays.stream(TipoContacto.values())
                .map(e -> new EnumOption(e.name(), e.getNombre()))
                .toList();
    }


    /** Todos los enums del formulario de una sola llamada */
    @GetMapping("/colaborador-form")
    public Map<String, List<EnumOption>> colaboradorForm() {
        return Map.of(
                "estadosCivil", estadosCivil(),
                "turnos", turnos(),
                "diasLaborales", diasLaborales(),
                "estatusColaborador", estatusColaborador(),
                "estados", estados()
        );
    }

}