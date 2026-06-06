package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository extends AbstractRepository<Cozinha, Long> {

    void remover(Long id);
    List<Cozinha> consultarPorNome(String nome);
}
