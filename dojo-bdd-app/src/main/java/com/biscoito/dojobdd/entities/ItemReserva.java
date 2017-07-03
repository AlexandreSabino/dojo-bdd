package com.biscoito.dojobdd.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemReserva {
    private String sku;

    private int quantidade;

    private String pais;

    private int almoxarifado;

    private String uuid;
}
