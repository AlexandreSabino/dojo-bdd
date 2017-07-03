package com.biscoito.dojobdd.usecases;

import com.biscoito.dojobdd.entities.ProdutoMovimentacao;
import com.biscoito.dojobdd.entities.SaldoProduto;
import com.biscoito.dojobdd.entities.TipoMovimentacao;
import com.biscoito.dojobdd.gateways.EstoqueGateway;
import com.biscoito.dojobdd.gateways.MovimentacaoGateway;
import com.biscoito.dojobdd.usecases.exceptions.ProdutoSemSaldoException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovimentarEstoque {

    private final EstoqueGateway estoqueGateway;

    private final MovimentacaoGateway movimentacaoGateway;

    public void execute(ProdutoMovimentacao produtoMovimentacao){

        if (produtoMovimentacao.getTipoMovimentacao() == TipoMovimentacao.PERDA){
            final SaldoProduto saldoProduto = estoqueGateway.procuraSaldoProduto(produtoMovimentacao.getSku(),
                    produtoMovimentacao.getAlmoxarifado(), produtoMovimentacao.getPais()).orElseThrow( () -> new RuntimeException("Deu ruim") );
            if (saldoProduto.getQuantidade() < produtoMovimentacao.getQuantidade()){
                throw new ProdutoSemSaldoException("Saldo insuficiente para efetuar a operação.");
            }
        }

        produtoMovimentacao.getTipoMovimentacao().execute(produtoMovimentacao, estoqueGateway);
        movimentacaoGateway.registrar(produtoMovimentacao);
    }

}
