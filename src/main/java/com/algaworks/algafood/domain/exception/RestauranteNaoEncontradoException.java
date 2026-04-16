package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends RuntimeException {

    public RestauranteNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
