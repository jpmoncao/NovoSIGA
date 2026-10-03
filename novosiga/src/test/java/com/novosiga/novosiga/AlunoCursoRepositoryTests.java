package com.novosiga.novosiga;

import static org.assertj.core.api.Assertions.assertThat;

import com.novosiga.novosiga.dto.AlunoCursoDTO;
import com.novosiga.novosiga.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlunoCursoRepositoryTests {
    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    void listaAlunosComOuSemFiltroDeCurso() {
        var todos = alunoRepository.listarPorCurso(null);
        assertThat(todos).hasSize((int) alunoRepository.count());
        assertThat(todos).allSatisfy(aluno -> assertThat(aluno.nomeAluno()).isNotBlank());

        todos.stream().map(AlunoCursoDTO::idCurso).filter(id -> id != null).findFirst()
                .ifPresent(id -> assertThat(alunoRepository.listarPorCurso(id))
                        .allSatisfy(aluno -> assertThat(aluno.idCurso()).isEqualTo(id)));
    }
}
