package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.entity.Laboral;
import com.emmadev.bungalows.entity.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NominaRepository extends JpaRepository<Nomina, Long> {
    List<Nomina> findByLaboral(Laboral laboral);
}
