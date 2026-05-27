package com.davalores.crypto.orchestrator.infra.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@ComponentScan(basePackages = {"com.davalores.crypto.orchestrator"})
@SecuritySchemes({
	@SecurityScheme( name = "basicAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "basic",
		in = SecuritySchemeIn.HEADER),
	@SecurityScheme(
		    name = "bearerAuth", // The name used to reference this scheme
		    type = SecuritySchemeType.HTTP,
		    scheme = "bearer",
		    bearerFormat = "JWT", // Optional, helps document the token type
		    in = SecuritySchemeIn.HEADER)
})
public class OpenAPIConfiguration {
	@Bean
	public OpenAPI defineOpenApi() {
		Server server = new Server();
		server.setUrl("/crypto/");
		server.setDescription("Server del Dominio seleccionado en la URL ");
		
		Contact myContact = new Contact();
		myContact.setName("DAValores");
		myContact.setEmail("soporte@davalores.com.ar");
		myContact.setUrl("https://davalores.com.ar/");	       
		   
		   
		Info information = new Info()
		           .title("DAValores - Middleware - Gestión Crypto Ripio")
		.version("1.0")
		.description("API Gestión Compra/Venta de Monedas Crypto ante Ripio")
        .contact(myContact);
		return new OpenAPI().info(information).servers(List.of(server));
	}
}
