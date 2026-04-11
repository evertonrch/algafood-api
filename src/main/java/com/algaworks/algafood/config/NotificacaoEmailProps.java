package com.algaworks.algafood.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "smtp") // classe para agrupar propriedades relacionadas ao servico de envio de email e centralizar em um so lugar
public class NotificacaoEmailProps {

    private String host; // apos o prefixo ele ja vai reconhecer, ex: smtp.host , o host ele vai fazer o bind automatico
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
