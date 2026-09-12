package com.novosiga.novosiga.service;

import java.util.List;

import com.novosiga.novosiga.repository.AlunoRepository;
import com.novosiga.novosiga.entity.Aluno;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public void deleteById(Integer id) {
        alunoRepository.deleteById(id);
    }

    public Aluno findById(Integer id) {
        return alunoRepository.findById(id).orElse(null);
    }

    public Aluno update(Aluno aluno) {
        Aluno alunoExistente = findById(aluno.getIdAluno());
        if (alunoExistente == null) {
            throw new RuntimeException("Aluno não encontrado");
        }

        alunoExistente.setNomeAluno(aluno.getNomeAluno());
        alunoExistente.setEnderecoAluno(aluno.getEnderecoAluno());
        alunoExistente.setBairroAluno(aluno.getBairroAluno());
        alunoExistente.setCidadeAluno(aluno.getCidadeAluno());
        alunoExistente.setEstadoAluno(aluno.getEstadoAluno());

        return alunoRepository.save(aluno);
    }
}
