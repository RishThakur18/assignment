package com.losung.assignment.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration

public class OpenApiConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("rishabhsingh97it@gmail.com");
        contact.setName("Rishabh Singh");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8013");
        localServer.setDescription("Local environment");

        Server productionServer = new Server();
        productionServer.setUrl("http://13.126.144.61:8080");
        productionServer.setDescription("Production environment");

        Info info = new Info()
                .title("Contacts MANAGER API")
                .contact(contact)
                .version("1.0")
                .description("These API exposes endpoints to manage contacts.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, productionServer));
    }
}
