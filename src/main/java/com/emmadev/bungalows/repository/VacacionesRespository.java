package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.entity.Vacaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacacionesRespository extends JpaRepository<Vacaciones, Long> {
}
