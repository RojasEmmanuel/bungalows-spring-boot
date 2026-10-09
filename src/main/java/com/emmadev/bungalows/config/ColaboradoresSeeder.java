package com.emmadev.bungalows.config;

import com.emmadev.bungalows.DTO.Colaborador.ColaboradorRequestComplete;
import com.emmadev.bungalows.Enums.*;
import com.emmadev.bungalows.entity.Puesto;
import com.emmadev.bungalows.entity.Ubicacion;
import com.emmadev.bungalows.repository.ColaboradorRepository;
import com.emmadev.bungalows.repository.PuestoRepository;
import com.emmadev.bungalows.repository.UbicacionRespository;
import com.emmadev.bungalows.service.ColaboradorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@Profile("dev")
@AllArgsConstructor
public class ColaboradoresSeeder implements CommandLineRunner {

    private final ColaboradorRepository colaboradorRepository;
    private final PuestoRepository puestoRepository;
    private final UbicacionRespository ubicacionRepository;
    private final ColaboradorService colaboradorService;

    private static final Random RNG = new Random(42); // semilla fija para reproducibilidad

    // ---------- Catálogos de datos ----------
    private static final String[] NOMBRES = {
            "Juan", "María", "José", "Ana", "Luis", "Laura", "Carlos", "Sofía",
            "Miguel", "Elena", "Pedro", "Carmen", "Jorge", "Lucía", "Ricardo",
            "Paula", "Fernando", "Daniela", "Roberto", "Isabel", "Alejandro",
            "Gabriela", "Diego", "Valeria", "Andrés", "Natalia", "Emilio",
            "Renata", "Sergio", "Camila", "Raúl", "Adriana", "Héctor",
            "Patricia", "Óscar", "Mónica", "Arturo", "Verónica", "Iván", "Regina"
    };

    private static final String[] APELLIDOS = {
            "García", "Rodríguez", "Martínez", "Hernández", "López", "González",
            "Pérez", "Sánchez", "Ramírez", "Torres", "Flores", "Rivera",
            "Gómez", "Díaz", "Reyes", "Morales", "Jiménez", "Ortiz", "Cruz",
            "Vargas", "Castillo", "Romero", "Mendoza", "Silva", "Núñez",
            "Rojas", "Medina", "Suárez", "Vega", "Ríos", "Campos", "Cortés",
            "Salazar", "Aguilar", "Fuentes", "Molina", "Castro", "Bautista",
            "Herrera", "Padilla", "Navarro", "Domínguez", "Rosales"
    };

    private static final String[] CALLES = {
            "Av. Juárez", "Calle Hidalgo", "Av. Reforma", "Calle Morelos",
            "Av. Independencia", "Calle 5 de Mayo", "Av. Constitución",
            "Calle Zaragoza", "Av. Insurgentes", "Calle Allende",
            "Av. Universidad", "Calle Madero", "Av. Revolución",
            "Calle Guerrero", "Av. Benito Juárez", "Calle Matamoros"
    };

    private static final String[] COLONIAS = {
            "Centro", "Reforma", "Del Valle", "Chapultepec", "Roma Norte",
            "Condesa", "Polanco", "Coyoacán", "Santa Fe", "Lomas de Chapultepec",
            "Nápoles", "Doctores", "Obrera", "Escandón", "San Rafael"
    };

    private static final String[] CIUDADES = {
            "Oaxaca", "Ciudad de México", "Guadalajara", "Monterrey", "Puebla",
            "Mérida", "Querétaro", "Tijuana", "León", "Cancún"
    };

    @Override
    public void run(String... args) {
        long existentes = colaboradorRepository.count();
        if (existentes > 0) {
            log.info("Ya existen {} colaboradores, saltando seeder", existentes);
            return;
        }

        List<Puesto> puestos = puestoRepository.findAll();
        List<Ubicacion> ubicaciones = ubicacionRepository.findAll();

        if (puestos.isEmpty() || ubicaciones.isEmpty()) {
            log.warn("No hay puestos o ubicaciones. Corre el seeder de esos primero.");
            return;
        }

        log.info("Generando 100 colaboradores…");
        int creados = 0;

        for (int i = 0; i < 100; i++) {
            try {
                ColaboradorRequestComplete dto = generarColaborador(puestos, ubicaciones);
                colaboradorService.saveColaborador(dto);
                creados++;
            } catch (Exception e) {
                log.error("Error al crear colaborador #{}: {}", i + 1, e.getMessage());
            }
        }

        log.info("Seeder creó {} colaboradores de 100 intentados", creados);
    }

    private ColaboradorRequestComplete generarColaborador(
            List<Puesto> puestos,
            List<Ubicacion> ubicaciones
    ) {
        String nombre = pick(NOMBRES);
        String ap = pick(APELLIDOS);
        String am = pick(APELLIDOS);

        // Edad entre 22 y 55 años
        LocalDate fechaNacimiento = LocalDate.now()
                .minusYears(22 + RNG.nextInt(33))
                .minusDays(RNG.nextInt(365));

        // Ingreso entre 1 y 8 años atrás
        LocalDate fechaIngreso = LocalDate.now()
                .minusYears(1 + RNG.nextInt(8))
                .minusDays(RNG.nextInt(365));

        // Sueldo entre 8000 y 35000
        BigDecimal sueldo = BigDecimal.valueOf(8000 + RNG.nextInt(27000))
                .setScale(2, java.math.RoundingMode.HALF_UP);

        Turnos turno = pick(Turnos.values());
        DiasLaborales diaDescanso = pick(DiasLaborales.values());
        EstatusColaborador estatus = pick(EstatusColaborador.values());

        LocalTime entrada = LocalTime.of(7 + RNG.nextInt(3), 0);
        LocalTime salida = entrada.plusHours(8);

        Puesto puesto = pick(puestos);
        Ubicacion ubicacion = pick(ubicaciones);

        // CURP y RFC sintéticos (NO reales, solo para pruebas)
        String curp = generarCurp(nombre, ap, am, fechaNacimiento);
        String rfc = generarRfc(nombre, ap, am, fechaNacimiento);
        String nss = String.format("%011d", RNG.nextInt(1_000_000_000));

        // Dirección
        String calle = pick(CALLES) + " " + (1 + RNG.nextInt(500));
        String colonia = pick(COLONIAS);
        String ciudad = pick(CIUDADES);
        Estados estado = pick(Estados.values());
        String cp = String.format("%05d", 10000 + RNG.nextInt(90000));

        return new ColaboradorRequestComplete(
                nombre,
                ap,
                am,
                fechaNacimiento,
                pick(EstadoCivil.values()),
                null, // fotografia
                fechaIngreso,
                sueldo,
                turno,
                diaDescanso,
                entrada,
                salida,
                estatus,
                puesto.getId(),
                ubicacion.getId(),
                curp,
                nss,
                rfc,
                calle,
                colonia,
                ciudad,
                estado,
                cp
        );
    }

    private <T> T pick(T[] arr) {
        return arr[RNG.nextInt(arr.length)];
    }

    private <T> T pick(List<T> list) {
        return list.get(RNG.nextInt(list.size()));
    }

    private String generarCurp(String nombre, String ap, String am, LocalDate fechaNac) {
        String iniciales = "" +
                ap.charAt(0) +
                primeraVocal(ap) +
                am.charAt(0) +
                nombre.charAt(0);
        String fecha = String.format("%02d%02d%02d",
                fechaNac.getYear() % 100,
                fechaNac.getMonthValue(),
                fechaNac.getDayOfMonth());
        String sexo = RNG.nextBoolean() ? "H" : "M";
        String estado = "DF"; // simplificado
        String consonantes = "" + consonante(ap) + consonante(am) + consonante(nombre);
        String random = String.format("%02d", RNG.nextInt(100));

        return (iniciales + fecha + sexo + estado + consonantes + random).toUpperCase();
    }

    private String generarRfc(String nombre, String ap, String am, LocalDate fechaNac) {
        String iniciales = "" + ap.charAt(0) + primeraVocal(ap) + am.charAt(0) + nombre.charAt(0);
        String fecha = String.format("%02d%02d%02d",
                fechaNac.getYear() % 100,
                fechaNac.getMonthValue(),
                fechaNac.getDayOfMonth());
        String random = String.format("%03d", RNG.nextInt(1000));
        return (iniciales + fecha + random).toUpperCase();
    }

    private char primeraVocal(String s) {
        for (char c : s.toUpperCase().toCharArray()) {
            if ("AEIOU".indexOf(c) >= 0) return c;
        }
        return 'X';
    }

    private char consonante(String s) {
        for (int i = 1; i < s.length(); i++) {
            char c = s.toUpperCase().charAt(i);
            if ("AEIOU".indexOf(c) < 0 && Character.isLetter(c)) return c;
        }
        return 'X';
    }
}