package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.dto.Nomina.NominaListadoResponse;
import com.emmadev.bungalows.dto.Nomina.NominaResumenResponse;
import com.emmadev.bungalows.entity.Laboral;
import com.emmadev.bungalows.entity.Nomina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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


    @Query("""
        SELECT new com.emmadev.bungalows.dto.Nomina.NominaResumenResponse(
            COUNT(n.id),
            COALESCE(SUM(n.subtotal), 0),
            COALESCE(SUM(n.descuentos), 0),
            COALESCE(SUM(n.retenciones), 0),
            COALESCE(SUM(n.total), 0)
        )
        FROM Nomina n
        WHERE
            (:ejercicio IS NULL OR n.ejercicio = :ejercicio)
            AND (:fechaInicio IS NULL OR n.fechaInicio >= :fechaInicio)
            AND (:fechaFin IS NULL OR n.fechaFin <= :fechaFin)
        """)
    NominaResumenResponse obtenerResumen(
            @Param("ejercicio") Integer ejercicio,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

    @Query(
            value = """
                SELECT DISTINCT n.ejercicio 
                FROM Nomina n 
                where n.ejercicio IS NOT NULL ORDER BY n.ejercicio DESC
            """
    )
    List<Integer> buscarEjerciciosDisponibles();

    @Query(
            value = """
        SELECT new com.emmadev.bungalows.dto.Nomina.NominaListadoResponse(
            n.id,
            TRIM(CONCAT(
                c.nombre, ' ',
                COALESCE(c.ap, ''), ' ',
                COALESCE(c.am, '')
            )),
            c.fotografia,
            n.ejercicio,
            n.fechaInicio,
            n.fechaFin,
            n.fechaPago,
            n.diasPago,
            n.subtotal,
            n.descuentos,
            n.retenciones,
            n.total
        )
        FROM Nomina n
        JOIN n.laboral l
        JOIN l.colaborador c
        WHERE (:ejercicio IS NULL OR n.ejercicio = :ejercicio)
          AND (:fechaInicio IS NULL OR n.fechaInicio >= :fechaInicio)
          AND (:fechaFin IS NULL OR n.fechaFin <= :fechaFin)
          AND (
              :colaborador IS NULL
              OR LOWER(
                  CONCAT(
                      c.nombre, ' ',
                      COALESCE(c.ap, ''), ' ',
                      COALESCE(c.am, '')
                  )
              ) LIKE LOWER(CONCAT('%', :colaborador, '%'))
          )
        """,
            countQuery = """
        SELECT COUNT(n.id)
        FROM Nomina n
        JOIN n.laboral l
        JOIN l.colaborador c
        WHERE (:ejercicio IS NULL OR n.ejercicio = :ejercicio)
          AND (:fechaInicio IS NULL OR n.fechaInicio >= :fechaInicio)
          AND (:fechaFin IS NULL OR n.fechaFin <= :fechaFin)
          AND (
              :colaborador IS NULL
              OR LOWER(
                  CONCAT(
                      c.nombre, ' ',
                      COALESCE(c.ap, ''), ' ',
                      COALESCE(c.am, '')
                  )
              ) LIKE LOWER(CONCAT('%', :colaborador, '%'))
          )
        """
    )
    Page<NominaListadoResponse> buscarNominas(
            @Param("ejercicio") Integer ejercicio,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            @Param("colaborador") String colaborador,
            Pageable pageable
    );

    @Query(
            value = """
        SELECT new com.emmadev.bungalows.dto.Nomina.NominaListadoResponse(
            n.id,
            TRIM(CONCAT(
                c.nombre, ' ',
                COALESCE(c.ap, ''), ' ',
                COALESCE(c.am, '')
            )),
            c.fotografia,
            n.ejercicio,
            n.fechaInicio,
            n.fechaFin,
            n.fechaPago,
            n.diasPago,
            n.subtotal,
            n.descuentos,
            n.retenciones,
            n.total
        )
        FROM Nomina n
        JOIN n.laboral l
        JOIN l.colaborador c
        WHERE c.id = :colaboradorId
        ORDER BY n.fechaPago DESC, n.id DESC
        """,
            countQuery = """
        SELECT COUNT(n.id)
        FROM Nomina n
        JOIN n.laboral l
        JOIN l.colaborador c
        WHERE c.id = :colaboradorId
        """
    )
    Page<NominaListadoResponse> buscarPorColaborador(
            @Param("colaboradorId") Long colaboradorId,
            Pageable pageable
    );
}
