package com.emmadev.bungalows.entity;

import com.emmadev.bungalows.Enums.Estados;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "direcciones")

public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String calle;
    @Column(length = 100)
    private String colonia;
    @Column(length = 100)
    private String cuidad;

    @Enumerated(EnumType.STRING)
    private Estados estado;

    @Column(length = 6)
    private String cp;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador colaborador;

}
