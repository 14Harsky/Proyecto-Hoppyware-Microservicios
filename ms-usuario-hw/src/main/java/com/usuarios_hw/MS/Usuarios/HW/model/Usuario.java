package com.usuarios_hw.MS.Usuarios.HW.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Table(name = "usuario")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String run;

    @Column(nullable = false)
    private String dvrun;

    @Column(nullable = false)
    private String pnombre;

    @Column(nullable = true)
    private String snombre;

    @Column(nullable = false)
    private String appaterno;

    @Column(nullable = false)
    private String apmaterno;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private String num_telefono;

    @Column(nullable = true)
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate fecha_nacto;

    

}
