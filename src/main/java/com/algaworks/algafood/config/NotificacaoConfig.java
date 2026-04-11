package com.algaworks.algafood.config;

import com.algaworks.algafood.service.Notificador;
import com.algaworks.algafood.service.NotificadorEmail;
import com.algaworks.algafood.service.ServidorSmtp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificacaoConfig {

    private final NotificacaoEmailProps props;

    public NotificacaoConfig(NotificacaoEmailProps props) {
        this.props = props;
    }

    @Bean
    public ServidorSmtp servidorSmtp() {
        var servidor = new ServidorSmtp(props.getHost(), props.getPort() ,"dfgdfg");
        System.out.println("Bean do servidorSmtp criado: " + servidor);
        return servidor;
    }

    @Bean
    public Notificador notificadorEmail(ServidorSmtp servidorSmtp) {
        return new NotificadorEmail(servidorSmtp);
    }
}
