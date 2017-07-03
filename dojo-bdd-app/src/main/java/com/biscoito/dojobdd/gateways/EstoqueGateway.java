package com.biscoito.dojobdd.gateways;

import com.biscoito.dojobdd.entities.SaldoProduto;

import java.util.List;
import java.util.Optional;

public interface EstoqueGateway {

    void decrementar(String sku, String pais, int almoxarifado, int quantidade);

    void incrementar(String sku, String pais, int almoxarifado, int quantidade);

    Optional<SaldoProduto> procuraSaldoProduto(String sku, int almoxarifado, String pais);

    List<SaldoProduto> procuraSaldoProduto(String sku, String pais);
}
