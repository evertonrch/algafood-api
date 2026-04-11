package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cozinha> buscarTodos() {
        return manager.createQuery("FROM Cozinha", Cozinha.class).getResultList();
    }

    @Override
    public Cozinha salvar(Cozinha entity) {
        return manager.merge(entity);
    }

    @Override
    public Cozinha buscarPorId(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Override
    public void remover(Cozinha entity) {
        manager.remove(entity);
    }
}
