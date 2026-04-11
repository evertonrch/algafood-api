package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_cozinha", schema = "public")
public class Cozinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Cozinha cozinha)) return false;

        return Objects.equals(id, cozinha.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("ID: %d - NOME: %s", id, nome);
    }
}
