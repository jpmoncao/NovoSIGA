package com.novosiga.novosiga.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemDoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    @ManyToOne
    @JoinColumn(name = "idprod", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "idpedido", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precoProduto;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    private BigDecimal calcularSubtotal() {
        return this.precoProduto.multiply(BigDecimal.valueOf(this.quantidade));
    }

    public void atualizarSubtotal() {
        this.subtotal = this.calcularSubtotal();
    }
}
