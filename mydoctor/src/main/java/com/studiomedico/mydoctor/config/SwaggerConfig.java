package com.studiomedico.mydoctor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                //gli do il titolo e la versione e una descrizione
                .title("REST API MyDoctor")
                .version("1.0")
                .description("Sistema di gestione delle prenotazioni per le visite mediche"));
    }
}
