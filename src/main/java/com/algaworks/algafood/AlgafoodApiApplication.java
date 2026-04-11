package com.algaworks.algafood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AlgafoodApiApplication {

    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AlgafoodApiApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
