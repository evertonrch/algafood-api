package com.algaworks.algafood;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlgafoodApiApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AlgafoodApiApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
