package com.eComm.eComm.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    // http://localhost:8080/api/v1.0/swagger-ui/index.html

    @Bean
    public OpenAPI mySwagerCustomeCongif(){

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Billing System")
                                .description("Backends API for Billing Application")
                                .version("1.0.0")
                )
                .servers(Arrays.asList
                        (new Server().url("http://localhost:8080/api/v1.0")
                                .description("Local Development"))
                )
                .tags(Arrays.asList(
                        new Tag().name("Auth API").description("Login and Registration"),
                        new Tag().name("Admin API").description("Manage Categories and Items"),
                        new Tag().name("User API").description("User operations"),
                        new Tag().name("Order API").description("Order placement and history"),
                        new Tag().name("Payment API").description("Razorpay integration"),
                        new Tag().name("Dashboard API").description("Analytics data")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                           new SecurityScheme()
                                   .type((SecurityScheme.Type.HTTP))
                                   .scheme("bearer")
                                   .bearerFormat("JWT")
                                   .in(SecurityScheme.In.HEADER)
                                   .name("Authorization")
                        ));

    }

}
