package com.algaworks.algafood.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {

    public EntidadeEmUsoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
