package com.teste.primeiroexemplo.view.model;

//Aqui será a resposta que irá ser retornada ao cliente após a requisição dele. Lembrando que caso não queira que alçguma informação volte para ele, basta excluir ela e seus métodos dessa classe
public class ProdutoResponse {
	
		private Integer id; 
		
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
