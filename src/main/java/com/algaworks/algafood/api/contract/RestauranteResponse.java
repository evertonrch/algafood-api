package com.algaworks.algafood.api.contract;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public record RestauranteResponse(
        Long id,
        String nome,
        BigDecimal taxaFrete,
        Cozinha cozinha,
        List<FormaPagamento> formasPagamento
) {

    public RestauranteResponse(Restaurante restaurante) {
        this(restaurante.getId(), restaurante.getNome(), restaurante.getTaxaFrete(), restaurante.getCozinha(), restaurante.getFormasPagamento());
    }
}
