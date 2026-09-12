package com.novosiga.novosiga.config;

import com.novosiga.novosiga.entity.Aluno;
import com.novosiga.novosiga.entity.Curso;
import com.novosiga.novosiga.entity.Disciplina;
import com.novosiga.novosiga.entity.Professor;
import com.novosiga.novosiga.repository.AlunoRepository;
import com.novosiga.novosiga.repository.CursoRepository;
import com.novosiga.novosiga.repository.DisciplinaRepository;
import com.novosiga.novosiga.repository.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProfessorRepository professorRepository;
    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final AlunoRepository alunoRepository;

    public DataLoader(ProfessorRepository professorRepository,
            CursoRepository cursoRepository,
            DisciplinaRepository disciplinaRepository,
            AlunoRepository alunoRepository) {
        this.professorRepository = professorRepository;
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    public void run(String... args) {
        if (professorRepository.count() > 0 || cursoRepository.count() > 0
                || disciplinaRepository.count() > 0 || alunoRepository.count() > 0) {
            return;
        }

        Professor ana = professorRepository.save(new Professor(null, "Ana Beatriz Costa",
                "52998224725", "329145678", "11987654321",
                "Rua das Acacias, 120 - Sao Paulo", "Mestrado em Ciencia da Computacao"));
        Professor carlos = professorRepository.save(new Professor(null, "Carlos Eduardo Lima",
                "39053344705", "412367890", "11976543210",
                "Av. Paulista, 900 - Sao Paulo", "Doutorado em Engenharia de Software"));
        Professor marina = professorRepository.save(new Professor(null, "Marina Souza Alves",
                "15350946056", "287654321", "11965432109",
                "Rua Augusta, 45 - Sao Paulo", "Mestrado em Sistemas de Informacao"));
        Professor rafael = professorRepository.save(new Professor(null, "Rafael Nunes Prado",
                "11144477735", "398712345", "11954321098",
                "Rua Vergueiro, 310 - Sao Paulo", "Especializacao em Banco de Dados"));

        Curso ads = cursoRepository.save(new Curso(null, "Analise e Desenvolvimento de Sistemas",
                "Formacao de profissionais para projetar, desenvolver e manter sistemas de software.", 2400));
        Curso si = cursoRepository.save(new Curso(null, "Sistemas de Informacao",
                "Curso voltado a gestao, analise e aplicacao de tecnologia da informacao nas organizacoes.", 3200));
        Curso cc = cursoRepository.save(new Curso(null, "Ciencia da Computacao",
                "Formacao em fundamentos de computacao, algoritmos, inteligencia artificial e sistemas.", 3600));

        disciplinaRepository.save(new Disciplina(null, "Programacao Orientada a Objetos", "POO", 80, ana, ads));
        disciplinaRepository.save(new Disciplina(null, "Banco de Dados", "BD", 80, rafael, ads));
        disciplinaRepository.save(new Disciplina(null, "Engenharia de Software", "ESW", 80, carlos, ads));
        disciplinaRepository.save(new Disciplina(null, "Gestao de Projetos", "GPR", 60, marina, si));
        disciplinaRepository.save(new Disciplina(null, "Arquitetura de Software", "ARS", 80, carlos, si));
        disciplinaRepository.save(new Disciplina(null, "Algoritmos e Estruturas de Dados", "AED", 80, ana, cc));
        disciplinaRepository.save(new Disciplina(null, "Inteligencia Artificial", "IA", 80, marina, cc));
        disciplinaRepository.save(new Disciplina(null, "Sistemas Distribuidos", "SD", 60, rafael, cc));

        alunoRepository.save(new Aluno(null, "Joao Pedro Silva", "Rua das Flores, 10",
                "Centro", "Sao Paulo", "SP", "01001000", "11988887777",
                "12345678909", null, null, ads));
        alunoRepository.save(new Aluno(null, "Luiza Fernandes", "Av. Brasil, 250",
                "Jardins", "Sao Paulo", "SP", "01415000", "11977776666",
                "98765432100", null, null, ads));
        alunoRepository.save(new Aluno(null, "Bruno Almeida", "Rua da Consolacao, 88",
                "Consolacao", "Sao Paulo", "SP", "01302000", "11966665555",
                "45678912300", null, null, si));
        alunoRepository.save(new Aluno(null, "Camila Rocha", "Rua Haddock Lobo, 15",
                "Cerqueira Cesar", "Sao Paulo", "SP", "01414000", "11955554444",
                "32165498700", null, null, si));
        alunoRepository.save(new Aluno(null, "Diego Martins", "Av. Ipiranga, 400",
                "Republica", "Sao Paulo", "SP", "01046010", "11944443333",
                "65498732100", null, null, cc));
        alunoRepository.save(new Aluno(null, "Fernanda Oliveira", "Rua da Paz, 72",
                "Bela Vista", "Sao Paulo", "SP", "01310000", "11933332222",
                "78912345600", null, null, cc));
    }
}
