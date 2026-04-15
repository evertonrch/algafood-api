package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cozinha> buscarTodos() {
        return manager.createQuery("FROM Cozinha", Cozinha.class).getResultList();
    }

    @Transactional
    @Override
    public Cozinha salvar(Cozinha entity) {
        return manager.merge(entity);
    }

    @Override
    public Cozinha buscarPorId(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    @Override
    public void remover(Cozinha entity) {
        manager.remove(entity);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Cozinha cozinha = buscarPorId(id);

        if (Objects.isNull(cozinha)) {
            throw new EmptyResultDataAccessException(1);
        }

        remover(cozinha);
    }
}
