package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.contract.RestauranteResponse;
import com.algaworks.algafood.api.contract.RestaurantesResponse;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;
    private final ObjectMapper objectMapper;

    public RestauranteController(RestauranteService restauranteService, ObjectMapper objectMapper) {
        this.restauranteService = restauranteService;
        this.objectMapper = objectMapper;
    }

    @GetMapping // collection resource
    public ResponseEntity<RestaurantesResponse> restaurantes() {
        var restaurantes = restauranteService.listar();
        return ResponseEntity.ok(new RestaurantesResponse(restaurantes));
    }

    @GetMapping("/{id}") // singleton resource
    public ResponseEntity<RestauranteResponse> getRestaurante(@PathVariable Long id) {
        try {
            var restaurante = restauranteService.getRestaurantePorId(id);
            return ResponseEntity.ok(new RestauranteResponse(restaurante));

        } catch (RestauranteNaoEncontradoException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = restauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(new RestauranteResponse(restaurante));
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        try {
            var restauranteExistente = restauranteService.getRestaurantePorId(id);

            // se a propriendade nao tiver o setter o Beanutils nao atribui
            BeanUtils.copyProperties(restaurante, restauranteExistente, "id");
            restauranteExistente = restauranteService.salvar(restauranteExistente);

            return ResponseEntity.ok(new RestauranteResponse(restauranteExistente));
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());

        } catch (RestauranteNaoEncontradoException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Restaurante restauranteAtual = restauranteService.getRestaurantePorId(id);

        if (Objects.isNull(restauranteAtual)) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, restauranteAtual);

        return atualizar(id, restauranteAtual);
    }

    @GetMapping("por-cozinha")
    public ResponseEntity<?> consultarRestaurantesDiferentesDe(@RequestParam String nome) {
        List<Restaurante> restaurantes = restauranteService.buscarRestaurantesPorCozinha(nome.toUpperCase());
        return ResponseEntity.ok(new RestaurantesResponse(restaurantes));
    }

    @GetMapping("primeiro-por-nome")
    public ResponseEntity<?> obterPrimeiroRestaurantePorNome(@RequestParam String nome) {
        try {
            Restaurante restaurante = restauranteService.obterPrimeiroRestPorNome(nome);
            return ResponseEntity.ok(restaurante);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("por-nome")
    public ResponseEntity<?> porNome(@RequestParam String nome, @RequestParam("cozinha_id") Long cozinhaId) {
        List<Restaurante> restaurantes = restauranteService.consultarPorNome(nome, cozinhaId);
        return ResponseEntity.ok(restaurantes);
    }


    @GetMapping("top2-por-nome")
    public ResponseEntity<?> top2RestaurantesPorNome(@RequestParam String nome) {
        List<Restaurante> restaurantes = restauranteService.obterTop2RestPorNome(nome);
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("contagem-por-cozinha")
    public ResponseEntity<?> top2RestaurantesPorNome(@RequestParam Long id) {
       Integer contagem = restauranteService.obterContagemRestaurantesPorCozinha(id);
       return ResponseEntity.ok(contagem);
    }

    @GetMapping("por-taxa")
    public ResponseEntity<?> buscarPorTaxaFrete(@RequestParam BigDecimal taxaInicial,
                                                @RequestParam BigDecimal taxaFinal) {
        List<Restaurante> restaurantes = restauranteService.buscarPorTaxaFrete(taxaInicial, taxaFinal);
        return ResponseEntity.ok(new RestaurantesResponse(restaurantes));
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteDestino) {
        Restaurante dadosRestaurante = objectMapper.convertValue(campos, Restaurante.class);

        campos.forEach((k, v) -> {
            Field campo = ReflectionUtils.findField(Restaurante.class, k);
            campo.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(campo, dadosRestaurante);
            ReflectionUtils.setField(campo, restauranteDestino, novoValor);
        });
    }
}
