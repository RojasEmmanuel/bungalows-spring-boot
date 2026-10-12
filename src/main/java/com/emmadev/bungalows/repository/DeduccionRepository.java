package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.entity.Deduccion;
import com.emmadev.bungalows.entity.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeduccionRepository extends JpaRepository<Deduccion, Long> {
    List<Deduccion> findByNomina(Nomina nomina);
}
