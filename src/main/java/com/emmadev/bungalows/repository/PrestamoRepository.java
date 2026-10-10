package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.Enums.EstatusPrestamo;
import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByColaboradorAndEstatusIn(
            Colaborador colaborador,
            Collection<EstatusPrestamo> estatus
    );

    List<Prestamo> findByEstatusIn(List<EstatusPrestamo> estatus);
}
