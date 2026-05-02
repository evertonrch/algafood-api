package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> buscarEstados() {
        return estadoRepository.buscarTodos();
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.salvar(estado);
    }

    public void remover(Estado estado) {
        try {
            estadoRepository.remover(estado);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("nao foi possivel excluir esse estado, pois ele esta associado a outra entidade.", ex);
        }
    }

    public Estado buscarPorId(Long id) {
        try {
            return estadoRepository.buscarPorId(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntidadeNaoEncontradaException("estado nao encontrado");
        }
    }
}
