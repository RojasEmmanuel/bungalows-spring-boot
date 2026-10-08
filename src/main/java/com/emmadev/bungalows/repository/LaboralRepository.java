package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.entity.Laboral;
import com.emmadev.bungalows.entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboralRepository extends JpaRepository<Laboral, Long> {
    List<Laboral> findByUbicacion(Ubicacion ubicacion);
}
