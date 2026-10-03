package com.novosiga.novosiga.repository;

import com.novosiga.novosiga.entity.Aluno;
import com.novosiga.novosiga.dto.AlunoCursoDTO;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    @Query("select new com.novosiga.novosiga.dto.AlunoCursoDTO(a.idAluno, a.nomeAluno, c.idCurso, c.nomeCurso) "
            + "from Aluno a left join a.curso c "
            + "where (:idCurso is null or c.idCurso = :idCurso) "
            + "order by case when c.idCurso is null then 1 else 0 end, c.nomeCurso asc, a.nomeAluno asc")
    List<AlunoCursoDTO> listarPorCurso(@Param("idCurso") Integer idCurso);
}
