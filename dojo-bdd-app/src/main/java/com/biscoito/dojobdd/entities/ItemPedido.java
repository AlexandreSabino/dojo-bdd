package com.biscoito.dojobdd.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemPedido {
    private String sku;

    private int quantidade;

    private String pais;
}
