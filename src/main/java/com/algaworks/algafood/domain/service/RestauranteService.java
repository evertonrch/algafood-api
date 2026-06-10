package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RestauranteService {

    private static final String COZINHA_NAO_EXISTE = "cozinha com o id %d nao existe.";
    private static final String RESTAURANTE_ID_NAO_EXISTE = "restaurante de id: %d nao existe.";
    private static final String RESTAURANTE_NAO_EXISTE = "restaurante com nome: '%s' nao encotrado.";

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    public RestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante getRestaurantePorId(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(RESTAURANTE_ID_NAO_EXISTE.formatted(id)));
    }

    public Restaurante salvar(Restaurante restaurante) {
        Cozinha cozinha = validarCozinha(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
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

        return restauranteRepository.save(existente);
    }

    public List<Restaurante> buscarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    public List<Restaurante> buscarRestaurantesPorCozinha(String cozinhaNome) {
        return restauranteRepository.findByCozinhaNomeContaining(cozinhaNome);
    }

    private Cozinha validarCozinha(Long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(COZINHA_NAO_EXISTE.formatted(id)));
    }

    public Restaurante obterPrimeiroRestPorNome(String nome) {
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(RESTAURANTE_NAO_EXISTE.formatted(nome)));
    }

    public List<Restaurante> obterTop2RestPorNome(String nome) {
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }
}
