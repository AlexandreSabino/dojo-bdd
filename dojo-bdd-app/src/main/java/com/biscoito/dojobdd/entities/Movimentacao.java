package com.biscoito.dojobdd.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class Movimentacao {

    private String uuid;

    private String sku;

    private int almoxarifado;

    private LocalDateTime data;

    private String pais;

    private int quantidade;

    private String descricao;


}
