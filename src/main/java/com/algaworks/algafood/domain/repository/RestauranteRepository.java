package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByTaxaFreteBetween(BigDecimal txInicial, BigDecimal txFinal);

    List<Restaurante> findByNomeAndCozinhaNome(String nomeRestaurante, String CozinhaNome);

    List<Restaurante> findByCozinhaNomeContaining(String nome);

    // findFirst = prefix, depois do prefix e antes do By pode ter qualquer coisa
    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    // findTop<N> = limit
    List<Restaurante> findTop2ByNomeContaining(String nome);

    // countBy = count, contagem de rest. por cozinha
    int countByCozinhaId(Long cozinhaId);
}
