package com.teste.primeiroexemplo.view.model;

//Essa classe define o que deve ser recebido pelo programa a partir do que o cliente manda para o projeto, no caso só devem conter os dados que o projeto espera receber para
//dar prosseguimento ao processo em questão. Isso vale para o corpo do objeto, pois caso a informação vá na URL, como no caso de busca por id no BD, então é o póprio cliente
//que insere, não necessitando colocar no modelo aqui.
public class ProdutoRequest {

	//private Integer id; O id não é necessário pois quando se cadastra o produto no BD, o id é gerado automaticamente, então ele não precisa ser recebido do cliente, então não deve constar aqui no model de request do produto já que o cliente não precisa alterar nada do BD.

	private String nome;

	private Integer quantidade;

	private Double valor;

	private String observacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
