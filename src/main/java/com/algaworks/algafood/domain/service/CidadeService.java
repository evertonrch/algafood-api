package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.salvar(cidade);
    }

    public List<Cidade> buscarTodos() {
        return cidadeRepository.buscarTodos();
    }

    public Cidade buscarPorId(Long id) {
        try {
            return cidadeRepository.buscarPorId(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException("restaurante de id: %d nao existe".formatted(id), ex);
        }
    }

    public void excluir(Long id) {
        try {
            Cidade cidade = buscarPorId(id);
            cidadeRepository.remover(cidade);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("cozinha de id %d nao pode ser removida, pois esta em uso.".formatted(id), ex);
        }
    }
}
