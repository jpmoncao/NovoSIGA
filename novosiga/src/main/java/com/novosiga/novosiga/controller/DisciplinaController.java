package com.novosiga.novosiga.controller;

import java.util.List;

import com.novosiga.novosiga.service.CursoService;
import com.novosiga.novosiga.service.DisciplinaService;
import com.novosiga.novosiga.service.ProfessorService;
import com.novosiga.novosiga.entity.Curso;
import com.novosiga.novosiga.entity.Disciplina;
import com.novosiga.novosiga.entity.Professor;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/disciplina")
public class DisciplinaController {
    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CursoService cursoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Disciplina disciplina) {
        disciplina.setProfessor(resolverProfessor(disciplina.getProfessor()));
        disciplina.setCurso(resolverCurso(disciplina.getCurso()));
        disciplinaService.save(disciplina);
        return "redirect:/disciplina/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Disciplina> disciplinas = disciplinaService.findAll();
        model.addAttribute("disciplinas", disciplinas);
        return "disciplina/listaDisciplinas";
    }

    @GetMapping("/criar")
    public String criarForm(Model model) {
        Disciplina disciplina = new Disciplina();
        disciplina.setProfessor(new Professor());
        disciplina.setCurso(new Curso());
        preencherFormulario(model, disciplina);
        return "disciplina/formularioDisciplina";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Disciplina disciplina = disciplinaService.findById(id);
        if (disciplina.getProfessor() == null) {
            disciplina.setProfessor(new Professor());
        }
        if (disciplina.getCurso() == null) {
            disciplina.setCurso(new Curso());
        }
        preencherFormulario(model, disciplina);
        return "disciplina/formularioDisciplina";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        disciplinaService.deleteById(id);
        return "redirect:/disciplina/listar";
    }

    private void preencherFormulario(Model model, Disciplina disciplina) {
        model.addAttribute("disciplina", disciplina);
        model.addAttribute("professores", professorService.findAll());
        model.addAttribute("cursos", cursoService.findAll());
    }

    private Professor resolverProfessor(Professor professor) {
        if (professor == null || professor.getIdProfessor() == null) {
            return null;
        }
        return professorService.findById(professor.getIdProfessor());
    }

    private Curso resolverCurso(Curso curso) {
        if (curso == null || curso.getIdCurso() == null) {
            return null;
        }
        return cursoService.findById(curso.getIdCurso());
    }
}
