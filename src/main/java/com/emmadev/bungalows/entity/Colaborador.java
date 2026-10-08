package com.emmadev.bungalows.entity;

import com.emmadev.bungalows.Enums.EstadoCivil;
import com.emmadev.bungalows.utils.FechaUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "colaboradores")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nombre;
    @Column(length = 100)
    private String ap;
    @Column(length = 100)
    private String am;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil;

    @Column(name = "fotografia_path", nullable = true)
    private String fotografia;

    // relacion uno a uno con su informacion confidencial, laboral y dirección.
    @OneToOne(mappedBy = "colaborador")
    private Confidencial confidencial;

    @OneToOne(mappedBy = "colaborador")
    private Laboral laboral;

    @OneToOne(mappedBy = "colaborador")
    private Direccion direccion;

    // relacion con su documentación, responsables,contactos, prestamos,...
    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentacion = new ArrayList<>();

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Responsable> responsables = new ArrayList<>();

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contacto> contactos = new ArrayList<>();

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos = new ArrayList<>();

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacacional> periodosVacacionales = new ArrayList<>();

    public String getNombreCompleto(){
        return nombre+" "+ap+" "+am;
    }
}
