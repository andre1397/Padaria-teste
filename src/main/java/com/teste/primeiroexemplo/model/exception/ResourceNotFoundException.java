package com.teste.primeiroexemplo.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)//Indica pro Spring que essa classe vai ser chamada quando ocorrer um not found (erro 404) da requisição
public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String mensagem) {
		super (mensagem);
	}
	
}
