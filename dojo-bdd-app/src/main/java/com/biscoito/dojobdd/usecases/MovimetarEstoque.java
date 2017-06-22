package com.biscoito.dojobdd.usecases;

import com.biscoito.dojobdd.entities.ProdutoMovimentacao;
import com.biscoito.dojobdd.gateways.MovimentacaoEstoqueGateway;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovimetarEstoque {

    private MovimentacaoEstoqueGateway movimentacaoEstoqueGateway;

    public void solicitar(final ProdutoMovimentacao produtoMovimentacao) {
        movimentacaoEstoqueGateway.save(produtoMovimentacao);
    }
}
