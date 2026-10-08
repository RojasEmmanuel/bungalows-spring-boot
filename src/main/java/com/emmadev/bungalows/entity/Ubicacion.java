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

@Table(name = "ubicaciones")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    private String direccion;

    @Column(name = "imagen_path")
    private String imagenPath;

    private String slug; // nombre de la ubicacion en minuscula y separado por comillas

    @OneToMany(mappedBy = "ubicacion")
    private List <Laboral> laborales = new ArrayList<>();


    @PrePersist
    public void prepersis(){
        this.slug = this.nombre.toLowerCase().replace(" ", "-");
    }

    @PreUpdate
    public void preupdate(){
        this.slug = this.nombre.toLowerCase().replace(" ", "-");
    }
}
