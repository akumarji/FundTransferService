package com.banking.service.FundTransferService.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Fund transfer app");

        Contact myContact = new Contact();
        myContact.setName("Amit Kumar");
        myContact.setEmail("jindal.amit47@gmail.com");

        Info information = new Info()
                .title("Fund transfer API")
                .version("1.0")
                .description("This API expose endpoints to manage fund transfer with exchange rate.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}