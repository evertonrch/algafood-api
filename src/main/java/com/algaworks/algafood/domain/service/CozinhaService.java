package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CozinhaService {

    private static final String ID_NAO_NULO = "nao existe cozinha com id: %d.";
    private static final String ENTIDADE_EM_USO = "cozinha de id %d nao pode ser removida, pois esta em uso.";

    private final CozinhaRepository cozinhaRepository;

    public CozinhaService(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long id) {
        try {
            cozinhaRepository.deleteById(id);

        } catch (EmptyResultDataAccessException ex) { // null entity
            throw new EntidadeNaoEncontradaException(ID_NAO_NULO.formatted(id), ex);

        } catch (DataIntegrityViolationException ex) { // foreign key constraint
            throw new EntidadeEmUsoException(ENTIDADE_EM_USO.formatted(id), ex);
        }

    }

    public boolean cozinhaExiste(String nome) {
        return cozinhaRepository.existsByNome(nome);
    }

    public List<Cozinha> buscarPorNome(String nome) {
        return cozinhaRepository.findByNomeContaining(nome);
    }
}
