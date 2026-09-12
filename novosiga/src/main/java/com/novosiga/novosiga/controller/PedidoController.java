package com.novosiga.novosiga.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.novosiga.novosiga.entity.Aluno;
import com.novosiga.novosiga.entity.ItemDoPedido;
import com.novosiga.novosiga.entity.Pedido;
import com.novosiga.novosiga.entity.Produto;
import com.novosiga.novosiga.service.AlunoService;
import com.novosiga.novosiga.service.PedidoService;
import com.novosiga.novosiga.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProdutoService produtoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Pedido pedido) {
        pedidoService.save(pedido);
        return "redirect:/pedido/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Pedido> pedidos = pedidoService.findAll();
        model.addAttribute("pedidos", pedidos);
        return "pedido/listaPedidos";
    }

    @GetMapping("/criar")
    public String criarForm(Model model) {
        Pedido pedido = new Pedido();
        pedido.setAluno(new Aluno());
        pedido.setData(LocalDate.now());
        pedido.setItens(new ArrayList<>());
        pedido.getItens().add(itemEmBranco());
        preencherFormulario(model, pedido);
        return "pedido/formularioPedido";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido.getAluno() == null) {
            pedido.setAluno(new Aluno());
        }
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            pedido.setItens(new ArrayList<>());
            pedido.getItens().add(itemEmBranco());
        } else {
            for (ItemDoPedido item : pedido.getItens()) {
                if (item.getProduto() == null) {
                    item.setProduto(new Produto());
                }
            }
        }
        preencherFormulario(model, pedido);
        return "pedido/formularioPedido";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        pedidoService.deleteById(id);
        return "redirect:/pedido/listar";
    }

    private ItemDoPedido itemEmBranco() {
        ItemDoPedido item = new ItemDoPedido();
        item.setProduto(new Produto());
        item.setQuantidade(1);
        return item;
    }

    private void preencherFormulario(Model model, Pedido pedido) {
        model.addAttribute("pedido", pedido);
        model.addAttribute("alunos", alunoService.findAll());
        model.addAttribute("produtos", produtoService.findAll());
    }
}
