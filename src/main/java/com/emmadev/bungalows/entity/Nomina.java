package com.emmadev.bungalows.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "nominas")
public class Nomina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ejercicio;
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "dias_pago")
    private Integer diasPago;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    private BigDecimal subtotal;
    private BigDecimal descuentos;
    private BigDecimal retenciones;
    private BigDecimal total;

    private String documentoPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboral_id")
    private Laboral laboral;

    @OneToMany(mappedBy = "nomina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Percepcion> percepciones = new ArrayList<>();

    @OneToMany(mappedBy = "nomina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deduccion> deducciones = new ArrayList<>();
}
