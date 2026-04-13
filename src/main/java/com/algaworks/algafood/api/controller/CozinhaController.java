package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.contract.CozinhasXMLContract;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;

    public CozinhaController(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    @GetMapping // collection resource
    public List<Cozinha> listarJSON() {
        return cozinhaRepository.buscarTodos();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE) // content negotiation
    public CozinhasXMLContract listarXML() {
        return new CozinhasXMLContract(cozinhaRepository.buscarTodos());
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE}) // singleton resource
    public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
        var cozinha = cozinhaRepository.buscarPorId(id);
        if (Objects.isNull(cozinha)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cozinha);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ProblemDetail handleNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE);
        detail.setDetail("nao existe negocicacao com esse media type.");
        return detail;
    }

}
