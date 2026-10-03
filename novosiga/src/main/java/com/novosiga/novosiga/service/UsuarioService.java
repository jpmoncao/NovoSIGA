package com.novosiga.novosiga.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.novosiga.novosiga.entity.Usuario;
import com.novosiga.novosiga.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public boolean loginDisponivel(String login) {
        return !repository.existsByLoginUsuario(login);
    }

    public Usuario cadastrar(Usuario usuario) {
        usuario.setIdUsuario(null);
        usuario.setRole("ROLE_USER");
        usuario.setSenhaUsuario(encoder.encode(usuario.getSenhaUsuario()));
        return repository.save(usuario);
    }
}
