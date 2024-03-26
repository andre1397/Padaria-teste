package com.teste.primeiroexemplo;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrimeiroExemploApplication {
	//Essa classe vai ser a responsável por startar toda a aplicação atraés do spring, sendo assim, ela já está toda configurada pelo spring para conseguir fazer isso

	public static void main(String[] args) {
		SpringApplication.run(PrimeiroExemploApplication.class, args);
	}

}
