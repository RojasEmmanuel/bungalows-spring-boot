package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.Enums.VacacionalEstatus;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Laboral;
import com.emmadev.bungalows.entity.Vacacional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacacionalRepository extends JpaRepository<Vacacional, Long> {
    Vacacional findByColaboradorAndEstatus(Colaborador colaborador, VacacionalEstatus estatus);
    boolean existsByColaboradorIdAndAnio(Long colaboradorId, Integer anio);
    List<Vacacional> findByEstatus(VacacionalEstatus estatus);
}
