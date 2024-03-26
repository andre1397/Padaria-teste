package com.teste.primeiroexemplo.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.primeiroexemplo.services.ProdutoService;
import com.teste.primeiroexemplo.shared.ProdutoDTO;
import com.teste.primeiroexemplo.view.model.ProdutoRequest;
import com.teste.primeiroexemplo.view.model.ProdutoResponse;

@RestController//Aqui está sendo dito ao Spring que essa classe aqui é um controller
@RequestMapping("/api/produtos")//Aqui é o endereço onde o Controller deve ficar escutando, ou seja, ele só vai ser acionado caso o endereço da url possua esse final entre os parenteses (/api/produtos nesse caso), caso não faça isso, o controller vai ser chamado toda vez que alguém acessar o endereço do projeto (localhost:8080 por exemplo)//O nome possui api/produtos por boas práticas de programação, no caso, o endereço pode ser qualquer um que voce queira colocar, mas como é uma api, o endereço possui api no nome e produtos, pois esse controller é justamente pra controlar a parte de produtos do projeto
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<List<ProdutoResponse>> obterTodos(){//O ResponseEntity lida com diferentes tipos de resposta
		List<ProdutoDTO> produtos = produtoService.obterTodos();//FAz a busca por todos os itens existentes no BD
		
		ModelMapper mapper = new ModelMapper();
		
		List<ProdutoResponse> resposta = produtos.stream()
				.map(produtoDto -> mapper.map(produtoDto, ProdutoResponse.class))//Pega cada item ProdutoDto da lista e converte pra ProdutoResponse, pois ele vai retornar pro cliente após a requisição, então é um response
				.collect(Collectors.toList());//transforma os objetos convertidos pra uma lista a ser retornada para o cliente
		
		return new ResponseEntity<>(resposta, HttpStatus.OK);//Aqui ele vai retornar a resposta em String mesmo e também o status code da requisição (OK ou 200) caso dê tudo certo
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<ProdutoResponse>> obterPorId(@PathVariable Integer id){
		Optional<ProdutoDTO> dto = produtoService.obterPorId(id);
		
		ProdutoResponse produtoResponse = new ModelMapper().map(dto, ProdutoResponse.class);
		
		return new ResponseEntity<>(Optional.of(produtoResponse), HttpStatus.OK);
		
		//A parte de cima é feita de um jeito que vai estourar uma exceção caso não encontre o objeto, a parte de baixo com try/catch
		//não estoura Exception, somente vai retornar ao cliente que não há copnteúdo a ser mostrado, pois não foi encontrado o objeto em questão.
		
		/*try {
			Optional<ProdutoDTO> dto = produtoService.obterPorId(id);
			
			ProdutoResponse produtoResponse = new ModelMapper().map(dto, ProdutoResponse.class);
			
			return new ResponseEntity<>(Optional.of(produtoResponse), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}*/
	}
	
	@PostMapping
	public ResponseEntity<ProdutoResponse> adicionar(@RequestBody ProdutoRequest produtoReq) {
		ModelMapper mapper = new ModelMapper();
		
		ProdutoDTO produtoDto = mapper.map(produtoReq, ProdutoDTO.class);
		
		produtoDto = produtoService.adicionar(produtoDto);
		
		return new ResponseEntity<>(mapper.map(produtoDto, ProdutoResponse.class), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResponse> atualizar(@RequestBody ProdutoRequest produtoReq, @PathVariable Integer id) {
		ModelMapper mapper = new ModelMapper();
		ProdutoDTO produtoDto = mapper.map(produtoReq, ProdutoDTO.class);
		produtoDto = produtoService.atualizar(id, produtoDto);
		return new ResponseEntity<>(mapper.map(produtoDto, ProdutoResponse.class), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Integer id) {//O ? diz que o método pode receber qualquer tipo de retorno
		produtoService.deletar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
