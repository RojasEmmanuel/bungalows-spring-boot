package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.entity.Nomina;
import com.emmadev.bungalows.entity.Percepcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PercepcionRepository extends JpaRepository<Percepcion, Long> {

    List<Percepcion> findByNomina(Nomina nomina);
}
