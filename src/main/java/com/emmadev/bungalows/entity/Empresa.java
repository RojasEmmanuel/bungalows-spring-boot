package com.emmadev.bungalows.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombreLegal;
    private String rfc;
    private String responsable;//nombre completo
    private String regimenFiscal;
    private String direccion;
    private String registroPatronal;
    private String logoPath;

    private LocalDate creacion;
    private LocalDate actualizado;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ubicacion> ubicaciones = new ArrayList<>();

    @PrePersist
    public void prepersist(){
        this.creacion = LocalDate.now();
        this.actualizado = LocalDate.now();
    }

    @PreUpdate
    public void preupdate(){
        this.actualizado = LocalDate.now();
    }
}

