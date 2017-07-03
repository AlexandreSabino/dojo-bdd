package com.biscoito.dojobdd.usecases;

import com.biscoito.dojobdd.entities.*;
import com.biscoito.dojobdd.gateways.EstoqueGateway;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ReservarEstoque {

    private final EstoqueGateway estoqueGateway;

    private final MovimentarEstoque movimentarEstoque;

    public Optional<ItemReserva> executar(final ItemPedido itemPedido) {
        final List<SaldoProduto> saldoProdutos = estoqueGateway.procuraSaldoProduto(itemPedido.getSku(), itemPedido.getPais());

        final Optional<SaldoProduto> saldoProdutoEncontrado = saldoProdutos.stream()
                .filter(SaldoProduto::isVenda)
                .filter(saldoProduto -> saldoProduto.getQuantidade() >= itemPedido.getQuantidade())
                .findFirst();

        if (saldoProdutoEncontrado.isPresent()){
            //Baixar Estoque
            ProdutoMovimentacao produtoMovimentacao = new ProdutoMovimentacao(itemPedido.getSku(), itemPedido.getPais(),
                    saldoProdutoEncontrado.get().getAlmoxarifado(), TipoMovimentacao.PERDA, itemPedido.getQuantidade(),
                    "Produto reservado com sucesso!");
            movimentarEstoque.execute(produtoMovimentacao);

            return  Optional.of(new ItemReserva(itemPedido.getSku(), itemPedido.getQuantidade(), itemPedido.getPais(),
                 saldoProdutoEncontrado.get().getAlmoxarifado(), UUID.randomUUID().toString()));
        }

        return Optional.empty();
    }
}
