package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsableRepository extends JpaRepository<Responsable, Long> {

    List<Responsable> findByColaborador(Colaborador colaborador);

}
