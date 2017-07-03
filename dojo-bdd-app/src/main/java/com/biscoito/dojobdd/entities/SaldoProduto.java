package com.biscoito.dojobdd.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaldoProduto {

    private String sku;

    private int quantidade;

    private int almoxarifado;

    private String pais;

    private boolean venda;

    public SaldoProduto(String sku, int quantidade, int almoxarifado, String pais){
        this.sku = sku;
        this.quantidade = quantidade;
        this.almoxarifado = almoxarifado;
        this.pais = pais;
    }

}
