package com.novosiga.novosiga.controller;

import java.io.OutputStream;
import java.util.List;

import com.novosiga.novosiga.entity.Aluno;
import com.novosiga.novosiga.service.AlunoService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @GetMapping
    public String index() {
        return "relatorios/index";
    }

    @GetMapping("/alunos")
    public String alunos(Model model) {
        List<Aluno> alunos = alunoService.findAll();
        model.addAttribute("alunos", alunos);
        return "relatorios/relatorioAlunos";
    }

    @GetMapping("/alunos/pdf")
    public void gerarPdf(HttpServletResponse response) throws Exception {
        List<Aluno> alunos = alunoService.findAll();

        Context context = new Context();
        context.setVariable("alunos", alunos);

        String html = templateEngine.process("relatorio/relatoriosAlunosPdf", context);
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=relatorio-alunos.pdf");

        OutputStream os = response.getOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, html);
        builder.toStream(os);
        builder.run();
        os.close();
    }
}
