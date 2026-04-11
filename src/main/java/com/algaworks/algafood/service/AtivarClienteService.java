package com.algaworks.algafood.service;

import com.algaworks.algafood.event.ClienteAtivadoEvent;
import com.algaworks.algafood.model.Cliente;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class AtivarClienteService {

    private ApplicationEventPublisher eventPublisher;

    public AtivarClienteService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void ativar(Cliente cliente) {
        cliente.setAtivar(true);

        // quero ter alta coesao e ter menos responsabilidade, ou seja, nao quero der dependecia com classes de notificacao
        // e quero desacoplar isso em eventos
//        notificador.notificar(cliente, String.format("Cliente %s foi ativado.", cliente.getNome()));

        eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
    }
}
