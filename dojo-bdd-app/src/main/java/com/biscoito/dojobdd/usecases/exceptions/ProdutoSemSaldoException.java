package com.biscoito.dojobdd.usecases.exceptions;

public class ProdutoSemSaldoException extends RuntimeException {
    public ProdutoSemSaldoException(String mensagem) {
        super(mensagem);
    }
}
