package com.algaworks.algafood.listener;

import com.algaworks.algafood.event.ClienteAtivadoEvent;
import com.algaworks.algafood.event.ClienteDesativadoEvent;
import com.algaworks.algafood.service.Notificador;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoListener {

    private final Notificador notificador;

    public NotificacaoListener(Notificador notificador) {
        this.notificador = notificador;
    }

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event) {
        notificador.notificar(event.getCliente(), "Cliente ativado");
    }

    @EventListener
    public void clienteDesativadoListener(ClienteDesativadoEvent event) {
        notificador.notificar(event.getCliente(), "Cliente desativado.");
    }
}
