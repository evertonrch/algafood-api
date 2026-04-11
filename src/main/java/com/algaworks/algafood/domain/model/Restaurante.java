package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_restaurante", schema = "public")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id") // padrao
    private Cozinha cozinha;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public Cozinha getCozinha() {
        return cozinha;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Restaurante that)) return false;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
