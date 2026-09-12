package com.novosiga.novosiga.repository;

import java.util.List;
import java.util.Optional;

import com.novosiga.novosiga.entity.Pedido;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @EntityGraph(attributePaths = { "aluno", "itens", "itens.produto" })
    @Override
    List<Pedido> findAll();

    @EntityGraph(attributePaths = { "aluno", "itens", "itens.produto" })
    @Override
    Optional<Pedido> findById(Integer id);
}
