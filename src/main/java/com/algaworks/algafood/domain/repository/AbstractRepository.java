package com.algaworks.algafood.domain.repository;

import java.util.List;

/**
 * Com o padrao repository, nao se cria um repository por tabela, e sim por Root Agregado (Aggreagate Root)
 * @param <T>
 * @param <ID>
 */
public interface AbstractRepository<T, ID> {

    List<T> buscarTodos();
    T salvar(T entity);
    T buscarPorId(ID id);
    void remover(T entity);
}
