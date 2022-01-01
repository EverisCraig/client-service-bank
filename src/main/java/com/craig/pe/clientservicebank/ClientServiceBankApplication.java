package com.craig.pe.clientservicebank;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Spring webflux api client",
        version = "1.0",
        description = "End points Client Microservice"
))
public class ClientServiceBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceBankApplication.class, args);
    }

}
