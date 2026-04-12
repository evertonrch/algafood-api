package com.algaworks.algafood.controller;

import com.algaworks.algafood.model.Cliente;
import com.algaworks.algafood.service.AtivarClienteService;
import com.algaworks.algafood.service.DesativarClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClienteController {

    private final AtivarClienteService ativarClienteService;
    private final DesativarClienteService desativarClienteService;

    public ClienteController(AtivarClienteService ativarClienteService, DesativarClienteService desativarClienteService) {
        this.ativarClienteService = ativarClienteService;
        this.desativarClienteService = desativarClienteService;
    }

    @GetMapping(produces = "text/plain")
    @ResponseBody
    public String ativarCliente() {
        var cliente = new Cliente("Everton", "everton@email.com", "55 555555555");
        ativarClienteService.ativar(cliente);

        return "cliente %s em processo de ativacao.".formatted(cliente.getNome());
    }

    @GetMapping(value = "/desativar", produces = "text/plain")
    @ResponseBody
    public void desativarCliente() {
        var cliente = new Cliente("Everton", "everton@email.com", "55 555555555");
        desativarClienteService.desativar(cliente);
    }
}
