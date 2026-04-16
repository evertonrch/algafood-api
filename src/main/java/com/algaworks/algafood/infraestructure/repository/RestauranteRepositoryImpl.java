package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> buscarTodos() {
        return manager.createQuery("FROM Restaurante", Restaurante.class).getResultList();
    }

    @Transactional
    @Override
    public Restaurante salvar(Restaurante entity) {
        return manager.merge(entity);
    }

    @Override
    public Restaurante buscarPorId(Long id) {
        Restaurante restaurante = manager.find(Restaurante.class, id);
        if (Objects.isNull(restaurante)) {
            throw new EmptyResultDataAccessException(1);
        }

        return restaurante;
    }

    @Transactional
    @Override
    public void remover(Restaurante entity) {
        manager.remove(entity);
    }
}
