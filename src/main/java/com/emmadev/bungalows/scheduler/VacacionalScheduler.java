package com.emmadev.bungalows.scheduler;

import com.emmadev.bungalows.DTO.Laboral.Aniversarios;
import com.emmadev.bungalows.entity.Laboral;
import com.emmadev.bungalows.repository.LaboralRepository;
import com.emmadev.bungalows.repository.VacacionalRepository;
import com.emmadev.bungalows.service.LaboralService;
import com.emmadev.bungalows.service.VacacionalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class VacacionalScheduler {

    private final LaboralService laboralService;
    private final VacacionalService vacacionalService;
    private final VacacionalRepository vacacionalRepository;
    private final LaboralRepository laboralRepository;

    /**
     * Corre todos los días a las 00:05 AM.
     * Cron: segundo minuto hora día-mes mes día-semana
     * 0 5 0 * * *  → 00:05 todos los días
     */
    //@Scheduled(cron = "0 5 0 * * *") // todos los dias  las 5:am
    @Scheduled(cron = "0 06 14 * * *")
    public void crearPeriodosVacacionalesDelDia() {
        int anioActual = LocalDate.now().getYear();
        log.info("=== Iniciando cron de periodos vacacionales ({}) ===", anioActual);

        List<Aniversarios> aniversarios = laboralService.getAniversarios();
        log.info("Aniversarios hoy: {}", aniversarios.size());

        int creados = 0;
        int omitidos = 0;

        for (Aniversarios a : aniversarios) {
            try {
                boolean yaExiste = vacacionalRepository
                        .existsByColaboradorIdAndAnio(a.id(), anioActual);

                if (yaExiste) {
                    log.debug("Ya existe periodo para colaborador {} en {}", a.id(), anioActual);
                    omitidos++;
                    continue;
                }

                // Cargar el Laboral del colaborador
                Laboral laboral = laboralRepository
                        .findById(a.id())
                        .orElseThrow(() -> new RuntimeException(
                                "No se encontró Laboral para el colaborador " + a.id()
                        ));

                vacacionalService.crearPeriodoVacacional(laboral);
                creados++;
                log.info("Periodo creado para colaborador {} ({})",
                        a.id(), a.nombreColaborador());

            } catch (Exception e) {
                log.error("Error al crear periodo para colaborador {}: {}",
                        a.id(), e.getMessage(), e);
            }
        }

        log.info("=== Cron finalizado. Creados: {}, Omitidos: {}, Total: {} ===",
                creados, omitidos, aniversarios.size());
    }
}