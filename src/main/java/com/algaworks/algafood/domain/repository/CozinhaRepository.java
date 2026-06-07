package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// nao precisa do @repository, pois a impl do JPA ja tem
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    // o spring data jpa, quando tem alguma consulta personalizada, tenta cria  uma implementacao padrao
    // em tempo de execucao, e cria as consultar de acordo com os NOMES DE PROPRIEDADES
    List<Cozinha> findByNomeContaining(String nome);
}
