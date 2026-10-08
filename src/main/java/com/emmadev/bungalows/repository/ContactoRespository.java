package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRespository extends JpaRepository<Contacto, Long> {
    List<Contacto> findByColaborador(Colaborador c);
}
