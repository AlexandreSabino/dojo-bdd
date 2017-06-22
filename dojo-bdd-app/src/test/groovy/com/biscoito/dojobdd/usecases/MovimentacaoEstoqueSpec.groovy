package com.biscoito.dojobdd.usecases

import com.biscoito.dojobdd.entities.OperacaoEstoque
import com.biscoito.dojobdd.entities.ProdutoMovimentacao
import com.biscoito.dojobdd.gateways.MovimentacaoEstoqueGateway
import spock.lang.Specification

class MovimentacaoEstoqueSpec extends Specification {

    def "movimentação de estoque decremento"() {
        given: "que seu sou estoquista"
        and: "estou analisando um produto"

        println(teste)
        MovimentacaoEstoqueGateway movimentacaoEstoqueGateway = Mock {
            1 * save(_ as ProdutoMovimentacao)
        }
        def movimentarEstoque = new MovimetarEstoque(movimentacaoEstoqueGateway)
        def produtoMovimentacao = new ProdutoMovimentacao("1234")

        when: "identificar uma perda no estoque "
        and: "desejar solicitar o decremento no estoque"
        produtoMovimentacao.setOperacaoEstoque(OperacaoEstoque.PERDA)
        movimentarEstoque.solicitar(produtoMovimentacao)

        then: "o sistema deve alterar o saldo do produto."

        where:
        teste | outro
        1     | 2
    }
}