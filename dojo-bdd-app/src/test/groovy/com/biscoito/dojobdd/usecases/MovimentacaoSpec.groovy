package com.biscoito.dojobdd.usecases

import com.biscoito.dojobdd.entities.Movimentacao
import com.biscoito.dojobdd.entities.ProdutoMovimentacao
import com.biscoito.dojobdd.entities.SaldoProduto
import com.biscoito.dojobdd.entities.TipoMovimentacao
import com.biscoito.dojobdd.gateways.EstoqueGateway
import com.biscoito.dojobdd.gateways.MovimentacaoGateway
import com.biscoito.dojobdd.usecases.exceptions.ProdutoSemSaldoException
import spock.lang.Specification

import java.time.LocalDateTime

class MovimentacaoSpec extends Specification {


    def "Perda de invetario"() {
        given: "que seu sou estoquista"
        and: "estou analisando um produto"
        EstoqueGateway estoqueGateway = Mock {
            1 * decrementar(_ , _ , _ , _ )
            procuraSaldoProduto(_ , _, _ ) >>> [criaSaldoProduto(sku, quantidadeInicial, almoxarifado, pais), criaSaldoProduto(sku, quantidadeResultado, almoxarifado, pais)]
        }

        MovimentacaoGateway movimentacaoGateway = Mock(MovimentacaoGateway) {
            1 * registrar(_ as ProdutoMovimentacao)
            procurarMovimentacoes(_, _, _ ) >> Arrays.asList(new Movimentacao(UUID.randomUUID().toString(), sku, almoxarifado, LocalDateTime.now(), pais, quantidadeMovimentada, "Deu ruim"))
        }

        def produtoMovimentacao = new ProdutoMovimentacao(sku, pais, almoxarifado, TipoMovimentacao.PERDA, quantidadeMovimentada, "Deu ruim")
        def movimentarEstoque = new MovimentarEstoque(estoqueGateway, movimentacaoGateway)
        def saldoProdutoAntigo = estoqueGateway.procuraSaldoProduto(produtoMovimentacao.sku, produtoMovimentacao.almoxarifado, produtoMovimentacao.pais)

        when: "identificar uma perda no estoque"
        and: "desejo solicitar o decremento no estoque "
        movimentarEstoque.execute(produtoMovimentacao)

        then: "o sistema deve alterar o saldo do produto"
        def saldoProdutoNovo = estoqueGateway.procuraSaldoProduto(produtoMovimentacao.sku, produtoMovimentacao.almoxarifado, produtoMovimentacao.pais)
        def movimentacoesEfetuadas = movimentacaoGateway.procurarMovimentacoes(produtoMovimentacao.sku, produtoMovimentacao.almoxarifado, produtoMovimentacao.pais)
        assert saldoProdutoAntigo.get().quantidade > saldoProdutoNovo.get().quantidade
        assert saldoProdutoAntigo.get().quantidade - produtoMovimentacao.quantidade == saldoProdutoNovo.get().quantidade

        def ultimaMovimentacao = movimentacoesEfetuadas.get(movimentacoesEfetuadas.size() - 1 )
        assert ultimaMovimentacao.quantidade == produtoMovimentacao.quantidade
        assert ultimaMovimentacao.sku == produtoMovimentacao.sku
        assert ultimaMovimentacao.pais == produtoMovimentacao.pais
        assert ultimaMovimentacao.almoxarifado == produtoMovimentacao.almoxarifado

        where:
        sku                 | quantidadeMovimentada | quantidadeResultado   | quantidadeInicial | almoxarifado | pais
        "000-0000-000-01"   | 2                     | 8                     | 10                | 9            | "BR"
        "000-0000-000-01"   | 1                     | 9                     | 10                | 9            | "BR"
        "000-0000-000-01"   | 3                     | 7                     | 10                | 9            | "BR"
    }

    def "Ganho de invetario"() {
        given: "que seu sou estoquista"
        and: "estou analisando um produto"
        EstoqueGateway estoqueGateway = Mock {
            1 * incrementar(_ , _ , _ , _ )
            procuraSaldoProduto(_ , _, _ ) >>> [criaSaldoProduto(sku, quantidadeInicial, almoxarifado, pais), criaSaldoProduto(sku, quantidadeResultado, almoxarifado, pais)]
        }

        MovimentacaoGateway movimentacaoGateway = Mock(MovimentacaoGateway) {
            1 * registrar(_ as ProdutoMovimentacao)
            procurarMovimentacoes(_, _, _ ) >> Arrays.asList(new Movimentacao(UUID.randomUUID().toString(), sku, almoxarifado, LocalDateTime.now(), pais, quantidadeMovimentada, "Deu bom"))
        }

        def produtoMovimentacao = new ProdutoMovimentacao(sku, pais, almoxarifado, TipoMovimentacao.GANHO, quantidadeMovimentada, "Deu bom")
        def movimentarEstoque = new MovimentarEstoque(estoqueGateway, movimentacaoGateway)
        def saldoProdutoAntigo = estoqueGateway.procuraSaldoProduto(produtoMovimentacao.sku, produtoMovimentacao.almoxarifado, produtoMovimentacao.pais)

        when: "identificar um produto a mais no estoque"
        and: "desejo solicitar o incremento no estoque "
        movimentarEstoque.execute(produtoMovimentacao)

        then: "o sistema deve alterar o saldo do produto"
        def saldoProdutoNovo = estoqueGateway.procuraSaldoProduto(produtoMovimentacao.sku, produtoMovimentacao.almoxarifado, produtoMovimentacao.pais)
        def movimentacoesEfetuadas = movimentacaoGateway.procurarMovimentacoes(produtoMovimentacao.sku, produtoMovimentacao.almoxarifado, produtoMovimentacao.pais)
        assert saldoProdutoAntigo.get().quantidade + produtoMovimentacao.quantidade == saldoProdutoNovo.get().quantidade

        def ultimaMovimentacao = movimentacoesEfetuadas.get(movimentacoesEfetuadas.size() - 1 )
        assert ultimaMovimentacao.quantidade == produtoMovimentacao.quantidade
        assert ultimaMovimentacao.sku == produtoMovimentacao.sku
        assert ultimaMovimentacao.pais == produtoMovimentacao.pais
        assert ultimaMovimentacao.almoxarifado == produtoMovimentacao.almoxarifado

        where:
        sku                 | quantidadeMovimentada | quantidadeResultado   | quantidadeInicial | almoxarifado | pais
        "000-0000-000-01"   | 2                     | 12                    | 10                | 9            | "BR"
        "000-0000-000-01"   | 1                     | 11                    | 10                | 9            | "BR"
        "000-0000-000-01"   | 3                     | 13                    | 10                | 9            | "BR"
    }

    def "Perda de invetario sem estoque"() {
        given: "que seu sou estoquista"
        and: "estou analisando um produto com estoque zerado no sistema"
        EstoqueGateway estoqueGateway = Mock {
            0 * decrementar(_ , _ , _ , _ )
            procuraSaldoProduto(_ , _, _ ) >>> [criaSaldoProduto(sku, quantidadeInicial, almoxarifado, pais), criaSaldoProduto(sku, quantidadeInicial, almoxarifado, pais)]
        }

        MovimentacaoGateway movimentacaoGateway = Mock(MovimentacaoGateway) {
            0 * registrar(_ as ProdutoMovimentacao)
            procurarMovimentacoes(_, _, _ ) >> Arrays.asList(new Movimentacao(UUID.randomUUID().toString(), sku, almoxarifado, LocalDateTime.now(), pais, quantidadeMovimentada, "Deu ruim"))
        }

        def produtoMovimentacao = new ProdutoMovimentacao(sku, pais, almoxarifado, TipoMovimentacao.PERDA, quantidadeMovimentada, "Deu ruim")
        def movimentarEstoque = new MovimentarEstoque(estoqueGateway, movimentacaoGateway)
        def saldoProdutoAntigo = estoqueGateway.procuraSaldoProduto(produtoMovimentacao.sku, produtoMovimentacao.almoxarifado, produtoMovimentacao.pais)

        when: "identificar um produto a menos no estoque"
        and: "solicitar o decremento"
        movimentarEstoque.execute(produtoMovimentacao)

        then: "o sistema deve validar se tem estoque suficiente para alterar o saldo"
        and: "apresentar o erro para o usuário"

        thrown ProdutoSemSaldoException

        where:
        sku                 | quantidadeMovimentada | quantidadeResultado   | quantidadeInicial | almoxarifado | pais
        "000-0000-000-01"   | 8                     | 8                     | 7                 | 9            | "BR"
        "000-0000-000-01"   | 9                     | 9                     | 8                 | 9            | "BR"
        "000-0000-000-01"   | 7                     | 7                     | 6                 | 9            | "BR"
    }

    def "Vai jogar Ex"() {
        given: "que seu sou estoquista"
        and: "estou analisando um produto com estoque zerado no sistema"
        EstoqueGateway estoqueGateway = Mock {
            0 * decrementar(_ , _ , _ , _ )
            procuraSaldoProduto(_ , _, _ ) >> Optional.empty()
        }

        MovimentacaoGateway movimentacaoGateway = Mock(MovimentacaoGateway) {
            0 * registrar(_ as ProdutoMovimentacao)
            procurarMovimentacoes(_, _, _ ) >> null
        }

        def produtoMovimentacao = new ProdutoMovimentacao("000-0000-000-01", "BR", 9, TipoMovimentacao.PERDA, 10, "Deu ruim")
        def movimentarEstoque = new MovimentarEstoque(estoqueGateway, movimentacaoGateway)

        when: "identificar um produto que nao existe no estoque"
        and: "solicitar o decremento"
        movimentarEstoque.execute(produtoMovimentacao)

        then: "o sistema deve validar se o produto existe"
        and: "apresentar o erro para o usuário"

        RuntimeException exception = thrown();
        assert exception.getMessage().equals("Deu ruim")

    }

    def criaSaldoProduto (String sku, int quantidade, int almoxarifado, String pais) {
        return Optional.of(new SaldoProduto(sku, quantidade, almoxarifado, pais))
    }
}
