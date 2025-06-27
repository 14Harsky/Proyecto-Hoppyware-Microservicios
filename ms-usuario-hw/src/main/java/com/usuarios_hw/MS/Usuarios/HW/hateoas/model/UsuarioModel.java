package com.usuarios_hw.MS.Usuarios.HW.hateoas.model;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

    private Long id;
    private String run;
    private String dvrun;
    private String pnombre;
    private String snombre;
    private String appaterno;
    private String apmaterno;
    private String correo;
    private String num_telefono;
    private Date fecha_nacto;

}
