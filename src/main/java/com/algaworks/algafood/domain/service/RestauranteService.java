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
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.buscarPorId(cozinhaId);

        if (Objects.isNull(cozinha)) {
            throw new EntidadeNaoEncontradaException("cozinha com o id %d nao existe".formatted(cozinhaId));
        }

        restaurante.setCozinha(cozinha);
        return restauranteRepository.salvar(restaurante);
    }
}
