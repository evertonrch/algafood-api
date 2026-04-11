package com.algaworks.algafood.service;

import com.algaworks.algafood.event.ClienteDesativadoEvent;
import com.algaworks.algafood.model.Cliente;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DesativarClienteService {

    private ApplicationEventPublisher eventPublisher;

    public DesativarClienteService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void desativar(Cliente cliente) {
        cliente.setAtivar(false);

        eventPublisher.publishEvent(new ClienteDesativadoEvent(cliente));
    }
}
