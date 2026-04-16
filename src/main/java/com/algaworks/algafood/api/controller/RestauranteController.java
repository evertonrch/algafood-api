package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.contract.RestauranteResponse;
import com.algaworks.algafood.api.contract.RestaurantesResponse;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
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
}
