package com.emmadev.bungalows.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "periodos_vacacionales")
public class Vacacional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dias_vacaciones")
    private Integer diasVacaciones;

    private Integer antiguedad;

    @Column(name = "dias_ocupados")
    private Integer diasOcupados;

    @Column(name = "dias_disponibles")
    private Integer dias_disponibles;

    private Integer anio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    @OneToMany(mappedBy = "periodoVacacional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacaciones> vacaciones = new ArrayList<>();
}
