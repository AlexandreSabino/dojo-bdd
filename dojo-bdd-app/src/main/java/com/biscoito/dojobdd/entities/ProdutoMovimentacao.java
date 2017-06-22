package com.biscoito.dojobdd.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ProdutoMovimentacao {

    private String sku;

    @Setter
    private OperacaoEstoque operacaoEstoque;

    public ProdutoMovimentacao(final String sku) {
        this.sku = sku;
    }
}
