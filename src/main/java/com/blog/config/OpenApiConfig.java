package com.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


//to open swagger UI...
//http://localhost:8080/swagger-ui/index.html#/
//and then in search box,..search for /v3/api-docs
@OpenAPIDefinition(
        info = @Info(
                title = "BLOG POST API",
                description = "This is an API for blogging",
                summary =  "This will create,delete,update",
                termsOfService = "T&C",
                contact = @Contact(
                        name = "Saurabh",
                        email = "Saurabhd785@gmail.com"
                ),
                license = @License(
                        name="Your License no."
                ),
                version = "v1"
        ),
        servers = {
                @Server(
                        description = "dev",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "dev1",
                        url = "http://localhost:8080"
                )
        },
        security = @SecurityRequirement(name="auth") //it will enable security on all Controller...so to enable only 1
        //controller, we can comment this line here  and put @SecurityRequirement(name="auth") above controller
)
//this will enable security on SWAGGER UI>.so that u can put JWT token in header and run request in Swagger UI.
@SecurityScheme(
        name="auth",
        in= SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme="bearer",
        description = "Security Description"
)
public class OpenApiConfig {
}
