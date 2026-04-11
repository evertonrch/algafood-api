package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_permissao", schema = "public")
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
}
