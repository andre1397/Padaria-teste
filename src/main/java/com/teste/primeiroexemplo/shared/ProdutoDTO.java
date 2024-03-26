package com.teste.primeiroexemplo.shared;

//Essa classe serve para transportar os dados do Produto model para o Controller sem a nacessidade do Controller ter acesso ao model diretamente, basicamente é um meio de campo entre eles, que faz uma cópia
//do model do produto e então essa cópia é enviada ao controller em vez do model original. model = original DTO = cópia

public class ProdutoDTO {

	private Integer id;

	private String nome;

	private Integer quantidade;

	private Double valor;

	private String observacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
