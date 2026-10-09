package com.emmadev.bungalows.entity;

import com.emmadev.bungalows.Enums.DiasLaborales;
import com.emmadev.bungalows.Enums.EstatusColaborador;
import com.emmadev.bungalows.Enums.Turnos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "datos_laborales") // Tabla que normaliza los datos laborales de un colaborador
public class Laboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;
    private BigDecimal sueldo;

    @Enumerated(EnumType.STRING)
    private Turnos turno;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_descanso")
    private DiasLaborales diaDescanso;

    private LocalTime entrada;
    private LocalTime salida;

    @Enumerated(EnumType.STRING)
    private EstatusColaborador estatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador colaborador;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puesto_id")
    private Puesto puesto;


    @OneToMany(mappedBy = "laboral", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacaciones> vacaciones = new ArrayList<>();

}


