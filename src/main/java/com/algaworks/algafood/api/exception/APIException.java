package com.algaworks.algafood.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIException {

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ProblemDetail handleMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE);
        detail.setDetail("nao existe negocicacao com esse media type.");
        return detail;
    }
}
