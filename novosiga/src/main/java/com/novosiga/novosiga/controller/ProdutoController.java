package com.novosiga.novosiga.controller;

import java.io.IOException;
import java.util.List;

import com.novosiga.novosiga.entity.Produto;
import com.novosiga.novosiga.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto,
            @RequestParam(value = "arquivoFoto", required = false) MultipartFile arquivoFoto) {
        if (arquivoFoto != null && !arquivoFoto.isEmpty()) {
            try {
                produto.setFoto(arquivoFoto.getBytes());
                produto.setTipoFoto(arquivoFoto.getContentType());
            } catch (IOException e) {
                System.out.println("Erro ao salvar foto: " + e.getMessage());
            }
        } else if (produto.getIdProduto() != null) {
            Produto produtoExistente = produtoService.findById(produto.getIdProduto());
            if (produtoExistente != null) {
                produto.setFoto(produtoExistente.getFoto());
                produto.setTipoFoto(produtoExistente.getTipoFoto());
            }
        }
        produtoService.save(produto);
        return "redirect:/produto/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Produto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        return "produto/listaProdutos";
    }

    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/formularioProduto";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        model.addAttribute("produto", produtoService.findById(id));
        return "produto/formularioProduto";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        produtoService.deleteById(id);
        return "redirect:/produto/listar";
    }

    @GetMapping("/foto/{idProduto}")
    public ResponseEntity<byte[]> getFoto(@PathVariable Integer idProduto) {
        Produto produto = produtoService.findById(idProduto);
        if (produto != null && produto.getFoto() != null) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(produto.getTipoFoto()))
                    .body(produto.getFoto());
        }
        return ResponseEntity.notFound().build();
    }
}
