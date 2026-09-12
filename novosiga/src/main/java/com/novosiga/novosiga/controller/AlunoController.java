package com.novosiga.novosiga.controller;

import java.io.IOException;
import java.util.List;

import com.novosiga.novosiga.service.AlunoService;
import com.novosiga.novosiga.service.CursoService;
import com.novosiga.novosiga.entity.Aluno;
import com.novosiga.novosiga.entity.Curso;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

@Controller
@RequestMapping("/aluno")
public class AlunoController {
    @Autowired
    private AlunoService alunoService;

    @Autowired
    private CursoService cursoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Aluno aluno,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {
        aluno.setCurso(resolverCurso(aluno.getCurso()));
        if (foto != null && !foto.isEmpty()) {
            try {
                aluno.setFotoAluno(foto.getBytes());
                aluno.setTipoFoto(foto.getContentType());
            } catch (IOException e) {
                System.out.println("Erro ao salvar foto: " + e.getMessage());
            }
        } else if (aluno.getIdAluno() != null) {
            Aluno alunoExistente = alunoService.findById(aluno.getIdAluno());
            if (alunoExistente != null) {
                aluno.setFotoAluno(alunoExistente.getFotoAluno());
                aluno.setTipoFoto(alunoExistente.getTipoFoto());
            }
        }
        alunoService.save(aluno);
        return "redirect:/aluno/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Aluno> alunos = alunoService.findAll();
        model.addAttribute("alunos", alunos);
        return "aluno/listaAlunos";
    }

    @GetMapping("/criar")
    public String criarForm(Model model) {
        Aluno aluno = new Aluno();
        aluno.setCurso(new Curso());
        preencherFormulario(model, aluno);
        return "aluno/formularioAluno";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Aluno aluno = alunoService.findById(id);
        if (aluno.getCurso() == null) {
            aluno.setCurso(new Curso());
        }
        preencherFormulario(model, aluno);
        return "aluno/formularioAluno";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        alunoService.deleteById(id);
        return "redirect:/aluno/listar";
    }

    @GetMapping("/foto/{idAluno}")
    public ResponseEntity<byte[]> getFoto(@PathVariable Integer idAluno) {
        Aluno aluno = alunoService.findById(idAluno);
        if (aluno.getFotoAluno() != null) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(aluno.getTipoFoto()))
                    .body(aluno.getFotoAluno());
        }
        return ResponseEntity.notFound().build();
    }

    private void preencherFormulario(Model model, Aluno aluno) {
        model.addAttribute("aluno", aluno);
        model.addAttribute("cursos", cursoService.findAll());
    }

    private Curso resolverCurso(Curso curso) {
        if (curso == null || curso.getIdCurso() == null) {
            return null;
        }
        return cursoService.findById(curso.getIdCurso());
    }
}
