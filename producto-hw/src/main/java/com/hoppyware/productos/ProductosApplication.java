package com.hoppyware.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@OpenAPIDefinition(
	info = @io.swagger.v3.oas.annotations.info.Info(
		title = "Productos Hoppyware",
		version = "1.0.0",
		description = "Gestión de productos en Hoppyware"
	)
)

@SpringBootApplication
public class ProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductosApplication.class, args);
	}

}
