package com.novosiga.novosiga.service;

import java.util.List;

import com.novosiga.novosiga.repository.ProfessorRepository;
import com.novosiga.novosiga.entity.Professor;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }

    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    public void deleteById(Integer id) {
        professorRepository.deleteById(id);
    }

    public Professor findById(Integer id) {
        return professorRepository.findById(id).orElse(null);
    }
}
