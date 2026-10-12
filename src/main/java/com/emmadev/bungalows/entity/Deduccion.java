package com.emmadev.bungalows.entity;

import com.emmadev.bungalows.Enums.TipoDeduccion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "deducciones_nomina")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Deduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoDeduccion tipo;

    private BigDecimal monto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nomina_id")
    private Nomina nomina;
}
