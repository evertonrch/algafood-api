package com.algaworks.algafood.domain.repository;

import java.util.List;

public interface AbstractRepository<T, ID> {

    List<T> buscarTodos();
    T salvar(T entity);
    T buscarPorId(ID id);
    void remover(T entity);
}
