package com.usuarios_hw.MS.Usuarios.HW.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.usuarios_hw.MS.Usuarios.HW.model.Usuario;

@DataJpaTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void contextLoads() {
        assertThat(usuarioRepository).isNotNull();
    }

    @Test
    void verificarSiLaTablaExiste() {
        System.out.println("Total usuarios: " + usuarioRepository.findAll().size());
    }

    @Test
    @DisplayName("Comprobacion de guardado de un Usuario en la base de datos")
    void guardarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setPnombre("Pascal");
        usuario.setSnombre("Alejandro");
        usuario.setAppaterno("Coloma");
        usuario.setApmaterno("Pulgar");
        usuario.setRun("19058428");
        usuario.setDvrun("3");
        usuario.setCorreo("holahola.cl");
        usuario.setNum_telefono("940861596");
        usuario.setFecha_nacto(LocalDate.of(1995, 8, 14));

        Usuario guardado = usuarioRepository.save(usuario);

        assertNotNull(guardado.getId());
    }

    @Test
    @DisplayName("Comprobando guardado y listado de Usuarios en el repositorio")
    void listarUsuarios() {
        Usuario usuario1 = new Usuario();

        usuario1.setPnombre("Ignacio");
        usuario1.setSnombre("Antonio");
        usuario1.setAppaterno("Araya");
        usuario1.setApmaterno("Tarnovksy");
        usuario1.setRun("12345678");
        usuario1.setDvrun("k");
        usuario1.setCorreo("holahola.cl");
        usuario1.setNum_telefono("912345678");

        Usuario usuario2 = new Usuario();
        usuario2.setFecha_nacto(LocalDate.of(1998, 4, 21));
        usuario2.setPnombre("Pascal");
        usuario2.setSnombre("Alejandro");
        usuario2.setAppaterno("Coloma");
        usuario2.setApmaterno("Pulgar");
        usuario2.setRun("19058428");
        usuario2.setDvrun("3");
        usuario2.setCorreo("holahola.cl");
        usuario2.setNum_telefono("940861596");
        usuario2.setFecha_nacto(LocalDate.of(1995, 8, 14));

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        List<Usuario> listaUsuarios = usuarioRepository.findAll();

        assertThat(listaUsuarios.size()).isGreaterThan(0);
        assertThat(listaUsuarios.get(0).getRun().equals(usuario1.getRun()));
        assertThat(listaUsuarios.get(1).getRun().equals(usuario2.getRun()));
    }

    @Test
    @DisplayName("Busqueda de usuario por RUN")
    void buscarPorRun(){
        Usuario usuario = new Usuario();
        usuario.setPnombre("Pascal");
        usuario.setSnombre("Alejandro");
        usuario.setAppaterno("Coloma");
        usuario.setApmaterno("Pulgar");
        usuario.setRun("19058428");
        usuario.setDvrun("3");
        usuario.setCorreo("holahola.cl");
        usuario.setNum_telefono("940861596");
        usuario.setFecha_nacto(LocalDate.of(1995, 8, 14));

        usuarioRepository.save(usuario);

        assertThat(usuarioRepository.findByRun("19058428")).isNotNull();
    }

    @Test
    @DisplayName("Eliminación de un usuario")
    void eliminarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setPnombre("Pascal");
        usuario.setSnombre("Alejandro");
        usuario.setAppaterno("Coloma");
        usuario.setApmaterno("Pulgar");
        usuario.setRun("19058428");
        usuario.setDvrun("3");
        usuario.setCorreo("holahola.cl");
        usuario.setNum_telefono("940861596");
        usuario.setFecha_nacto(LocalDate.of(1995, 8, 14));

        Usuario guardado = usuarioRepository.save(usuario);

        usuarioRepository.delete(guardado);

        assertThat(usuarioRepository.findByRun(guardado.getRun())).isNull();
    }

}
