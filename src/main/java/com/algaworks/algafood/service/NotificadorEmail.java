package com.algaworks.algafood.service;

import com.algaworks.algafood.model.Cliente;

public class NotificadorEmail implements Notificador{

    private ServidorSmtp servidorSmtp;

    public NotificadorEmail(ServidorSmtp servidorSmtp) {
        this.servidorSmtp = servidorSmtp;
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        String saida = String.format("Notificando o cliente %s com o email %s e mensagem: %s%n",
                cliente.getNome(), cliente.getEmail(), mensagem);

        System.out.printf(saida);
    }
}
