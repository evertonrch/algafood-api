package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_cidade", schema = "public")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Cidade estado)) return false;

        return Objects.equals(id, estado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
