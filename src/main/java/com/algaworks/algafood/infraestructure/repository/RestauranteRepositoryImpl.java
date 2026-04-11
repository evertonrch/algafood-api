package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> buscarTodos() {
        return manager.createQuery("FROM Restaurante", Restaurante.class).getResultList();
    }

    @Override
    public Restaurante salvar(Restaurante entity) {
        return manager.merge(entity);
    }

    @Override
    public Restaurante buscarPorId(Long id) {
        return manager.find(Restaurante.class, id);
    }

    @Override
    public void remover(Restaurante entity) {
        manager.remove(entity);
    }
}
