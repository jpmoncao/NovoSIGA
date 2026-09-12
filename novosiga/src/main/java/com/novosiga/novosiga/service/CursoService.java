package com.novosiga.novosiga.service;

import java.util.List;

import com.novosiga.novosiga.repository.CursoRepository;
import com.novosiga.novosiga.entity.Curso;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public void deleteById(Integer id) {
        cursoRepository.deleteById(id);
    }

    public Curso findById(Integer id) {
        return cursoRepository.findById(id).orElse(null);
    }
}
