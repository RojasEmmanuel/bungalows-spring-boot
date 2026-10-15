package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.entity.Laboral;
import com.emmadev.bungalows.entity.Nomina;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NominaRepository extends JpaRepository<Nomina, Long> {

    List<Nomina> findByLaboral(Laboral laboral);

    @EntityGraph(attributePaths = {
            "percepciones",
            "laboral",
            "laboral.colaborador",
            "laboral.colaborador.confidencial",
            "laboral.puesto",
            "laboral.ubicacion",
            "laboral.ubicacion.empresa"
    })
    @Query("SELECT n FROM Nomina n WHERE n.id = :id")
    Optional<Nomina> findWithPercepcionesById(@Param("id") Long id);

    @EntityGraph(attributePaths = {
            "deducciones",
            "laboral",
            "laboral.colaborador",
            "laboral.colaborador.confidencial",
            "laboral.puesto",
            "laboral.ubicacion",
            "laboral.ubicacion.empresa"
    })
    @Query("SELECT n FROM Nomina n WHERE n.id = :id")
    Optional<Nomina> findWithDeduccionesById(@Param("id") Long id);
}
