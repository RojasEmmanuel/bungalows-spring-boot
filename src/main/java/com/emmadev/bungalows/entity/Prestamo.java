package com.emmadev.bungalows.entity;

import com.emmadev.bungalows.Enums.EstatusPrestamo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal monto;
    private BigDecimal montoPagado; // al inicio es 0
    private String concepto;

    @Column(name = "fecha_prestamo")
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_pago", nullable = true)
    private LocalDate fechaPago;

    private EstatusPrestamo estatus;

    @Column(nullable = true)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    @PrePersist // al registrar un nuevo prestamo por defautl se registra como prestado
    private void prepersist(){
        this.estatus = EstatusPrestamo.PRESTADO;
        this.montoPagado = BigDecimal.ZERO;
    }
}
