package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.entity.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRespository extends JpaRepository<Ubicacion, Long> {
}
