package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Estado> buscarTodos() {
        return manager.createQuery("from Estado", Estado.class)
                .getResultList();
    }

    @Transactional
    @Override
    public Estado salvar(Estado estado) {
        return manager.merge(estado);
    }

    @Override
    public Estado buscarPorId(Long id) {
        Estado estado = manager.find(Estado.class, id);

        if (Objects.isNull(estado)) {
            throw new EmptyResultDataAccessException(1);
        }

        return estado;
    }

    @Transactional
    @Override
    public void remover(Estado estado) {
        manager.remove(estado);
    }
}
