package com.example.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "API-SWAGGER",version = "2.0.4"),
		servers = {@Server(url = "http://172.20.10.5:8080", description = "Default "),
				@Server(url = "http://192.168.1.4:8080", description = "Nhà"),
				@Server(url = "http://192.168.1.56:8080", description = "Nhà5"),
				@Server(url = "http://192.168.1.160:8080", description = "sapa"),
				@Server(url = "http://192.168.1.22:8080", description = "thongtha"),
				@Server(url = "http://172.16.0.155:8080", description = "trường"),
				@Server(url = "http://192.168.1.137:8080", description = "Dongtay"),
				@Server(url = "http://192.168.105.242:8080", description = "tvDongtay")},
		tags = {@Tag(name = "My shop" , description = "My Swagger.")}
)

@SecurityScheme(name = "BearerJWT", type = SecuritySchemeType.HTTP,scheme = "bearer", bearerFormat = "JWT",
description = "Brearer token for project books store")
public class MyShopApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(MyShopApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
				.allowedOrigins("http://192.168.1.22:8080")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*")
				.allowCredentials(false)
				.maxAge(3600);
	}
}
