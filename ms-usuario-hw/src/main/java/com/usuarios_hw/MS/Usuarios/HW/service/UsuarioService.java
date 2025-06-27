package com.usuarios_hw.MS.Usuarios.HW.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.usuarios_hw.MS.Usuarios.HW.dto.*;

import com.usuarios_hw.MS.Usuarios.HW.repository.UsuarioRepository;
import com.usuarios_hw.MS.Usuarios.HW.model.Usuario;
import java.util.List;


@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PedidoClient pedidoClient;

    public UsuarioService(PedidoClient pedidoClient){
        this.pedidoClient = pedidoClient;
    }

    

    public List<PedidoDto> getPedidoPorIdUsr(Long id){
        return pedidoClient.getPedidoPorUsr(id);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public <Optional>Usuario findById(Long id){
        return usuarioRepository.findById(id).get();
    }

    public Usuario findByRun(String run){
        return usuarioRepository.findByRun(run);
    }

    public List<Usuario> saveLista(List<Usuario> listUsuarios){
        return usuarioRepository.saveAll(listUsuarios);
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario registrarUsuario(Usuario usuario){
        String telefono = usuario.getNum_telefono();

        if(!telefono.matches("^\\d{8}$")){
            throw new IllegalArgumentException("Número debe tener 8 dígitos");
        }

        telefono = "+569" + telefono;
        usuario.setNum_telefono(telefono);

        return usuarioRepository.save(usuario);
    }

    public void delete(Long id){
        usuarioRepository.deleteById(id);
    }
    
}
