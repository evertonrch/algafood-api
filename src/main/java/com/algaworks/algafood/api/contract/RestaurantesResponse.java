package com.algaworks.algafood.api.contract;

import com.algaworks.algafood.domain.model.Restaurante;

import java.util.List;


public class RestaurantesResponse {

    private final List<RestauranteResponse> restaurantesResponse;

    public RestaurantesResponse(List<Restaurante> restaurantes) {
        restaurantesResponse = restaurantes.stream().map(RestauranteResponse::new).toList();
    }

    public List<RestauranteResponse> getRestaurantesResponse() {
        return restaurantesResponse;
    }
}
