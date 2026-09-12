package com.novosiga.novosiga.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.novosiga.novosiga.entity.Aluno;
import com.novosiga.novosiga.entity.ItemDoPedido;
import com.novosiga.novosiga.entity.Pedido;
import com.novosiga.novosiga.entity.Produto;
import com.novosiga.novosiga.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private AlunoService alunoService;

    @Transactional
    public Pedido save(Pedido formulario) {
        Pedido pedido = formulario.getIdPedido() != null
                ? pedidoRepository.findById(formulario.getIdPedido()).orElse(new Pedido())
                : new Pedido();

        if (pedido.getItens() == null) {
            pedido.setItens(new ArrayList<>());
        }
        pedido.getItens().clear();

        pedido.setData(formulario.getData());
        pedido.setAluno(resolverAluno(formulario.getAluno()));

        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        if (formulario.getItens() != null) {
            for (ItemDoPedido itemFormulario : formulario.getItens()) {
                if (itemFormulario.getProduto() == null || itemFormulario.getProduto().getIdProduto() == null) {
                    continue;
                }
                if (itemFormulario.getQuantidade() == null || itemFormulario.getQuantidade() < 1) {
                    continue;
                }

                Produto produto = produtoService.findById(itemFormulario.getProduto().getIdProduto());
                if (produto == null) {
                    continue;
                }

                ItemDoPedido item = new ItemDoPedido();
                item.setPedido(pedido);
                item.setProduto(produto);
                item.setQuantidade(itemFormulario.getQuantidade());
                item.setPrecoProduto(produto.getValor());
                item.setSubtotal(produto.getValor()
                        .multiply(BigDecimal.valueOf(itemFormulario.getQuantidade()))
                        .setScale(2, RoundingMode.HALF_UP));
                pedido.getItens().add(item);
                total = total.add(item.getSubtotal());
            }
        }

        pedido.setTotal(total);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public void deleteById(Integer id) {
        pedidoRepository.deleteById(id);
    }

    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    private Aluno resolverAluno(Aluno aluno) {
        if (aluno == null || aluno.getIdAluno() == null) {
            return null;
        }
        return alunoService.findById(aluno.getIdAluno());
    }
}
