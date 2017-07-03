package com.biscoito.dojobdd.usecases

import com.biscoito.dojobdd.entities.ItemPedido
import com.biscoito.dojobdd.entities.Movimentacao
import com.biscoito.dojobdd.entities.ProdutoMovimentacao
import com.biscoito.dojobdd.entities.SaldoProduto
import com.biscoito.dojobdd.gateways.EstoqueGateway
import com.biscoito.dojobdd.gateways.MovimentacaoGateway
import spock.lang.Specification

import java.time.LocalDateTime

class ReservaSpec extends Specification {

    def "Reservar estoque"() {
        given: "que sou um cliente"
        and: "efetuo uma compra com apenas um produto"
        EstoqueGateway estoqueGateway = Mock {
            1 * decrementar(_ , _ , _ , _ )
            procuraSaldoProduto(_ , _, _ ) >>> [criaSaldoProduto(sku, quantidadeInicial, almoxarifado, pais), criaSaldoProduto(sku, quantidadeResultado, almoxarifado, pais)]
        }

        MovimentacaoGateway movimentacaoGateway = Mock(MovimentacaoGateway) {
            1 * registrar(_ as ProdutoMovimentacao)
            procurarMovimentacoes(_, _, _ ) >> Arrays.asList(new Movimentacao(UUID.randomUUID().toString(), sku, almoxarifado, LocalDateTime.now(), pais, quantidadeMovimentada, "Deu ruim"))
        }
        def itemPedido = new ItemPedido("xxx-xxxx-xxx-xx", 100, "BR")
        def reservarEstoque = new ReservarEstoque(estoqueGateway, movimentacaoGateway)

        when: "o sistema deve reservar o estoque do produto com o status RESERVADO"
        def itemReserva = reservarEstoque.executar(itemPedido)

        then: "e decrementar o estoque disponivel do produto"
        assert itemReserva.quantidade == itemPedido.quantidade
        assert itemReserva.sku == itemPedido.sku
        assert itemReserva.almoxarifado == 9
        assert itemReserva.pais == itemPedido.pais

        where:
        sku                 | quantidadeMovimentada | quantidadeResultado   | quantidadeInicial | almoxarifado | pais
        "000-0000-000-01"   | 2                     | 8                     | 10                | 9            | "BR"

    }

    def criaSaldoProduto (String sku, int quantidade, int almoxarifado, String pais) {
        return Optional.of(new SaldoProduto(sku, quantidade, almoxarifado, pais))
    }
}
