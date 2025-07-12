package com.alura.literalura;

import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.GutendexClient;
import com.alura.literalura.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LiteraluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(LibroRepository libroRepository, AutorRepository autorRepository, GutendexClient gutendexClient) {
		return args -> {
			LibroService libroService = new LibroService(gutendexClient, libroRepository, autorRepository);
			Principal principal = new Principal(libroService, libroRepository, autorRepository);
			principal.mostrarMenu();
		};
	}
}
