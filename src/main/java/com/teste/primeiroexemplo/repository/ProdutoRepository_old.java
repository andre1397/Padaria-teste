package com.teste.primeiroexemplo.repository;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.teste.primeiroexemplo.model.Produto;

//Essa classe específica não está ligada a um BD, ela só foi feita de exemplo, então só simula como se estivesse ligada a um repositório
@Repository//Essa anotação mostra pro Spring que essa classe é um repositório, ou seja, que ela quem vai intermediar a comunicação com o BD, com a anotação feita, o Spring vai automatizar a instanciação da classe, issose chama injeção de dependências, pois a anotação está inserindo a dependencia do spring aqui dentro
public class ProdutoRepository_old {
	private List<Produto> produtos = new ArrayList<Produto>();
	private Integer ultimoId = 0;
	
	/** 
	 * Método que retorna uma lista dos produtos cadastrrados no Banco de Dados
	 * 
	 * @return Lista de Produtos
	 * */
	//O comentário acima é um javadoc, pra usá-lo é /** e Enter, a diferença dele para um comentário comum como esse aqui é que o javadoc aparece quando se passa o mouse em cima do método, seja qual for a classe em que ele está sendo inserido, sendo assim, é muito importante para documentar o funcionamento do programa
	public List<Produto> obterTodos(){
		return produtos;
	}
	
	/**
	 * Método que retorna o produto de acordo com o id informado no parâmetro do método
	 * @param id do produto que será localizado
	 * @return retorna um produto caso seja encontrado seu id
	 */
	public Optional<Produto> obterPorId(Integer id){//A classe Optional tem um papel parecido com o List, mas com a diferença que se não for encontrado nenhum produto no método, ele não dará uma Exception e só retornará um valor null ou vazio.
		return produtos
				.stream()
				.filter(produto -> produto.getId() == id)
				.findFirst();//Aqui basicamente ele pega todos os produtos presentes na lista e filtra pelo id posto no parâmetro do método, então pega o primeiro produto que encontrar com aquele id e o retorna.
	}
	
	public Produto adicionar(Produto produto) {
		//Aqui tá sendo simulado como se tivesse sendo lterado o BD, no caso, a lista produtos seria o BD
		ultimoId++;
		
		produto.setId(ultimoId);
		produtos.add(produto);
		
		return produto;
	}
	
	public void deletar(Integer id) {
		produtos.removeIf(produto -> produto.getId() == id);
	}
	
	public Produto atualizar(Produto produto) {
		//encontra o produto antigo
		Optional<Produto> produtoEncontrado = obterPorId(produto.getId());//Usa o método de encontrar o produto pelo id pra achar o produto antigo, está sendo usado Optional pq existe a possibilidade do produto não ser encontrado, caso o id seja inserido errado por exemplo
		
		if(produtoEncontrado.isEmpty()) {//veridica se o produto foi encontrado, como é um Optional a classe do produtoEncontrado, então ele vai retornar vazio caso não encontre
			throw new InputMismatchException("Produto não encontrado");//Caso não encontre o produto ele vai lançar uma Exception dizendo que o produto não foi encontrado
		}
		
		//remove o produto antigo
		deletar(produto.getId());
		
		//Adiciona na lista o produto atualizado no lugar do antigo
		produtos.add(produto);//Aqui daria pra usar o método de adicionar dessa mesma classe, porém nesse caso aqui não seria possível pois o método adicionar feito aqui incrementa o id, sendo assim, foi melhor criar um adicionar do zero pra utilizar o mesmo id que já produto pq a intenção do atualizar é justamente atualizar o produto deixando ele com o mesmo id
		
		return produto;
	}
}
