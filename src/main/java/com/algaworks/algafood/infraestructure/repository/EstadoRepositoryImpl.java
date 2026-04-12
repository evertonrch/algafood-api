package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Estado> buscarTodos() {
        return manager.createQuery("from Estado", Estado.class)
                .getResultList();
    }

    @Override
    public Estado salvar(Estado entity) {
        return null;
    }

    @Override
    public Estado buscarPorId(Long aLong) {
        return null;
    }

    @Override
    public void remover(Estado entity) {

    }
}
