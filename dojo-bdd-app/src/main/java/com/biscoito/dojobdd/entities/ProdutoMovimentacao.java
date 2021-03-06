package com.biscoito.dojobdd.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoMovimentacao {

    private String sku;

    private String pais;

    private int almoxarifado;

    private TipoMovimentacao tipoMovimentacao;

    private int quantidade;

    private String descricao;
}
