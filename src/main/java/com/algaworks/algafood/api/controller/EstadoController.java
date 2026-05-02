package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public ResponseEntity<List<Estado>> listar() {
        return ResponseEntity.ok(estadoService.buscarEstados());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Estado estado, UriComponentsBuilder uriBuilder) {
        var salved = estadoService.salvar(estado);

        URI location = uriBuilder.path("/estados/{id}").buildAndExpand(salved.getId()).toUri();
        return ResponseEntity.created(location).body(salved);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
        try {
            Estado estadoExistente = estadoService.buscarPorId(id);
            BeanUtils.copyProperties(estado, estadoExistente, "id");
            estadoExistente = estadoService.salvar(estadoExistente);

            return ResponseEntity.ok(estadoExistente);

        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // entidades que possuem relacionamento podem gerar conflito, pois existem uma constraint no banco,
    // independente do tipo de relacionamento (1-1, 1-N ou N-N)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            Estado estado = estadoService.buscarPorId(id);
            estadoService.remover(estado);

            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException ex) {
            ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
            detail.setDetail(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(detail);
        }
    }
}
