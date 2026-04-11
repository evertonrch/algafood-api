package com.algaworks.algafood.event;

import com.algaworks.algafood.model.Cliente;

public class ClienteDesativadoEvent {

    private Cliente cliente;

    public ClienteDesativadoEvent(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
