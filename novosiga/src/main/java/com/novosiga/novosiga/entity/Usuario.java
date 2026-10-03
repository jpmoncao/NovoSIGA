package com.novosiga.novosiga.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idUsuario;
    @Column(nullable = false, length = 40)
    private String nomeUsuario;
    @Column(nullable = false, length = 11)
    private String cpfUsuario;
    @Column(nullable = false, unique = true, length = 30)
    private String loginUsuario;
    @Column(nullable = false, length = 150)
    private String senhaUsuario;
    @Column(nullable = false)
    private String role = "ROLE_USER";
}
