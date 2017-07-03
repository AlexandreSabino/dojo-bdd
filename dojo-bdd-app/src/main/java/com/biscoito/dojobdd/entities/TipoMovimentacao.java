package com.biscoito.dojobdd.entities;

import com.biscoito.dojobdd.gateways.EstoqueGateway;

import java.util.function.BiConsumer;



public enum TipoMovimentacao {

    PERDA((produtoMovimentacao, estoqueGateway) -> estoqueGateway.decrementar(produtoMovimentacao.getSku(), produtoMovimentacao.getPais(),
            produtoMovimentacao.getAlmoxarifado(), produtoMovimentacao.getQuantidade())),
    GANHO((produtoMovimentacao, estoqueGateway) -> estoqueGateway.incrementar(produtoMovimentacao.getSku(), produtoMovimentacao.getPais(),
            produtoMovimentacao.getAlmoxarifado(), produtoMovimentacao.getQuantidade()));

    private final BiConsumer < ProdutoMovimentacao, EstoqueGateway > movimenta;

    TipoMovimentacao(BiConsumer < ProdutoMovimentacao, EstoqueGateway > movimenta){
        this.movimenta = movimenta;
    }

    public void execute(ProdutoMovimentacao produtoMovimentacao, EstoqueGateway estoqueGateway) {
        this.movimenta.accept(produtoMovimentacao, estoqueGateway);
    }

}

