package com.novosiga.novosiga.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDisciplina;

    @Column(nullable = false, length = 100)
    private String nomeDisciplina;

    @Column(nullable = false, length = 10)
    private String siglaDisciplina;

    @Column(nullable = false)
    private Integer cargaHorariaDisciplina;

    @ManyToOne
    @JoinColumn(name = "idProfessor")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "idCurso")
    private Curso curso;
}
