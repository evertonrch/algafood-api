package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    public CidadeService(CidadeRepository cidadeRepository, EstadoRepository estadoRepository) {
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    public Cidade salvar(Cidade cidade) {
        try {
            Estado estado = estadoRepository.buscarPorId(cidade.getEstado().getId());
            cidade.setEstado(estado);

            return cidadeRepository.salvar(cidade);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException(String.format("estado '%s' nao encontrado", cidade.getEstado().getNome().toUpperCase()));
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("estado ja em uso", ex);
        }
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
