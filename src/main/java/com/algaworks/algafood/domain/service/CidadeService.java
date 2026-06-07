package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    private static final String ID_NAO_EXISTE = "cidade de id: %d nao existe";
    private static final String ESTADO_NAO_ENCONTRADO = "estado '%s' nao encontrado";
    private static final String CIDADE_EM_USO = "cidade de id %d nao pode ser removida, pois esta em uso.";

    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    public CidadeService(CidadeRepository cidadeRepository, EstadoRepository estadoRepository) {
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    public Cidade salvar(Cidade cidade) {
        Optional<Estado> estado = estadoRepository.findById(cidade.getEstado().getId());
        if (estado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format(ESTADO_NAO_ENCONTRADO, cidade.getNomeEstadoUpper()));
        }

        cidade.setEstado(estado.get());
        return cidadeRepository.save(cidade);
    }

    public List<Cidade> buscarTodos() {
        return cidadeRepository.findAll();
    }

    public Cidade buscarPorId(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(ID_NAO_EXISTE.formatted(id)));
    }

    public void excluir(Long id) {
        try {
            Cidade cidade = buscarPorId(id);
            cidadeRepository.delete(cidade);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(CIDADE_EM_USO.formatted(id), ex);
        }
    }
}
