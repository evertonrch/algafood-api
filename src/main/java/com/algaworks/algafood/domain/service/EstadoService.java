package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    private static final String VIOLACAO_INTEGRIDADE = "nao foi possivel excluir esse estado, pois ele esta associado a outra entidade.";

    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> buscarEstados() {
        return estadoRepository.findAll();
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void remover(Estado estado) {
        try {
            estadoRepository.delete(estado);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(VIOLACAO_INTEGRIDADE, ex);
        }
    }

    public Estado buscarPorId(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("estado nao encontrado"));
    }
}
