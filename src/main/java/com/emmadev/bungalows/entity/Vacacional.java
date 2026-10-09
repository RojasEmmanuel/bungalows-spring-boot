package com.emmadev.bungalows.entity;

import com.emmadev.bungalows.Enums.VacacionalEstatus;
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
@Table(
        name = "periodos_vacacionales",
        uniqueConstraints = @UniqueConstraint( // solo un vacacional al año por colaborador
                name = "uk_vacacional_colaborador_anio",
                columnNames = { "colaborador_id", "anio" }
        )
)
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
    private Integer diasDisponibles;

    private Integer anio;

    @Enumerated(EnumType.STRING)
    private VacacionalEstatus estatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    @OneToMany(mappedBy = "periodoVacacional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacaciones> vacaciones = new ArrayList<>();

    @PrePersist
    public void prepersist(){
        this.estatus = VacacionalEstatus.DISPONIBLE;
    }
}
