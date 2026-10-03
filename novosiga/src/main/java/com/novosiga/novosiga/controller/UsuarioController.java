package com.novosiga.novosiga.controller;

import com.novosiga.novosiga.entity.Usuario;
import com.novosiga.novosiga.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) { this.service = service; }

    @GetMapping("/criar")
    public String criar(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/formularioUsuario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario, Model model) {
        if (usuario.getNomeUsuario() == null || usuario.getNomeUsuario().isBlank()
                || usuario.getCpfUsuario() == null || !usuario.getCpfUsuario().matches("\\d{11}")
                || usuario.getLoginUsuario() == null || usuario.getLoginUsuario().isBlank()
                || usuario.getSenhaUsuario() == null || usuario.getSenhaUsuario().isBlank()
                || !service.loginDisponivel(usuario.getLoginUsuario())) {
            model.addAttribute("erro", "Confira os dados e escolha um login disponível.");
            return "usuario/formularioUsuario";
        }
        service.cadastrar(usuario);
        return "redirect:/login?cadastrado";
    }
}
