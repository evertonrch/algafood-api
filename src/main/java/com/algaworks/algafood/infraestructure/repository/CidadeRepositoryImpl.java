package com.algaworks.algafood.infraestructure.repository;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Cidade> buscarTodos() {
        return em.createQuery("from Cidade", Cidade.class).getResultList();
    }

    @Transactional
    @Override
    public Cidade salvar(Cidade cidade) {
        return em.merge(cidade);
    }

    @Override
    public Cidade buscarPorId(Long id) {
        Cidade cidade =  em.find(Cidade.class, id);

        if (Objects.isNull(cidade)) {
            throw new EmptyResultDataAccessException(1);
        }
        return cidade;
    }

    @Transactional
    @Override
    public void remover(Cidade entity) {
        em.remove(entity);
    }
}
