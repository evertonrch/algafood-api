package com.algaworks.algafood.dao;

import com.algaworks.algafood.domain.model.Cozinha;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CozinhaDao {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> getCozinhas() {
        return manager.createQuery("from Cozinha", Cozinha.class)
                .getResultList();
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    @Transactional
    public void remover(Cozinha cozinha) {
        int id = cozinha.getId().intValue();
        cozinha = manager.find(Cozinha.class, cozinha.getId());

        if (cozinha == null) {
            System.err.printf("cozinha de id %d nao encontrada", id);
            return;
        }

        manager.remove(cozinha);
    }
  }
