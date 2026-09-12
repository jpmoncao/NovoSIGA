package com.novosiga.novosiga.entity;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProfessor;

    @Column(nullable = false, length = 50)
    private String nomeProfessor;

    @Column(nullable = false, length = 11)
    private String cpfProfessor;

    @Column(nullable = false, length = 11)
    private String rgProfessor;

    @Column(nullable = false, length = 11)
    private String telefoneProfessor;

    @Column(nullable = false, length = 100)
    private String enderecoProfessor;

    @Column(nullable = false, length = 100)
    private String graduacaoProfessor;
}
