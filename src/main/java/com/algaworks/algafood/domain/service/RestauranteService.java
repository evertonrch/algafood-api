package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    public RestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
    }

    public List<Restaurante> listar() {
        return restauranteRepository.buscarTodos();
    }

    public Restaurante getRestaurantePorId(Long id) {
        try {
            return restauranteRepository.buscarPorId(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new RestauranteNaoEncontradoException("restaurante de id: %d nao existe".formatted(id), ex);
        }
    }

    public Restaurante salvar(Restaurante restaurante) {
        Cozinha cozinha = validarCozinha(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return restauranteRepository.salvar(restaurante);
    }

    public Restaurante atualizar(Restaurante existente, Restaurante restauranteAtualizado) {
        if (restauranteAtualizado.getNome() != null) {
            existente.setNome(restauranteAtualizado.getNome());
        }
        if (restauranteAtualizado.getTaxaFrete() != null) {
            existente.setTaxaFrete(restauranteAtualizado.getTaxaFrete());
        }
        if (restauranteAtualizado.getCozinha().getId() != null) {
            Cozinha cozinha = validarCozinha(restauranteAtualizado.getCozinha().getId());
            existente.setCozinha(cozinha);
        }

        return restauranteRepository.salvar(existente);
    }

    private Cozinha validarCozinha(Long id) {
        Cozinha cozinha = cozinhaRepository.buscarPorId(id);
        if (Objects.isNull(cozinha)) {
            throw new EntidadeNaoEncontradaException("cozinha com o id %d nao existe".formatted(id));
        }

        return cozinha;
    }
}
