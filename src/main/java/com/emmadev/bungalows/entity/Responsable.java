package com.emmadev.bungalows.entity;

import com.emmadev.bungalows.Enums.Parentescos;
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
@Table(name = "contacto_responsable") // guarada todos los contactos de responsables en caso de emergencia
public class Responsable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String nombre;

    @Column(length = 15)
    private String telefono;

    @Enumerated(EnumType.STRING)
    private Parentescos parentesco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;
}
