package com.emmadev.bungalows.entity;

import com.emmadev.bungalows.Enums.EstatusVacaciones;
import com.emmadev.bungalows.service.PuestoService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vacaciones")
public class Vacaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "dias_ocupados")
    private Integer diasOcupados;


    @Enumerated(EnumType.STRING)
    private EstatusVacaciones estatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacacional_id")
    private Vacacional periodoVacacional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboral_id")
    private Laboral laboral;
}
