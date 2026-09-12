package com.novosiga.novosiga.repository;

import com.novosiga.novosiga.entity.Aluno;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    
}
