package com.biscoito.dojobdd.gateways;

import com.biscoito.dojobdd.entities.Movimentacao;
import com.biscoito.dojobdd.entities.ProdutoMovimentacao;

import java.util.List;

public interface MovimentacaoGateway {
    void registrar(ProdutoMovimentacao produtoMovimentacao);

    List<Movimentacao> procurarMovimentacoes(String sku, int almoxarifado, String pais);
}
