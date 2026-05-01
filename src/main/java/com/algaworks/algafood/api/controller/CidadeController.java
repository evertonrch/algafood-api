package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeService cidadeService;
    private static final String X_RESULT_SIZE = "X-RESULT-SIZE";

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> cidades() {
        var cidades = cidadeService.buscarTodos();

        HttpHeaders headers = new HttpHeaders();
        headers.add(X_RESULT_SIZE, String.valueOf(cidades.size()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(cidades);
    }

    /**
     * validar o estado que vem junto com a cidade, relacionamento 1 - 1, nao permiti que um estado faca parte
     * de mais de uma cidade
     */

    @PostMapping
    public ResponseEntity<Cidade> adicionar(@RequestBody Cidade cidade, UriComponentsBuilder uriBuilder) {
        var salved = cidadeService.salvar(cidade);

        URI location = uriBuilder.path("/cidades/{id}").buildAndExpand(salved.getId()).toUri();
        return ResponseEntity.created(location).body(salved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cidadeService.buscarPorId(id);

        if (Objects.isNull(cidadeAtual)) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        cidadeAtual = cidadeService.salvar(cidade);
        return ResponseEntity.ok(cidadeAtual);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            cidadeService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
