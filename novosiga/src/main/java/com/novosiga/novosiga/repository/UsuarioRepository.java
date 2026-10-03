package com.novosiga.novosiga.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.novosiga.novosiga.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByLoginUsuario(String loginUsuario);
    boolean existsByLoginUsuario(String loginUsuario);
}
