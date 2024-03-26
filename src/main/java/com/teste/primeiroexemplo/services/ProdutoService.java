package com.teste.primeiroexemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.primeiroexemplo.model.Produto;
import com.teste.primeiroexemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiroexemplo.repository.IProdutoRepository;
import com.teste.primeiroexemplo.shared.ProdutoDTO;

/*O service do projeto é onde fica o código que faz as coisas propriamente dito, nesse caso aqui, como o único propósito do projeto é guardar produtos no BD, então o services é quem fica responsável
por chamar os métodos pertencentes ao Repository e assim cadastrar os produtos no BD, e exista alguma regra de negócio (regras que o produto precisa seguir pra ser cadastrado como valor acima de R$ 5,00 por exemplo)
é aqui onde vão ficar elas e não na classe do repository, pois lá é exclusivamente pra gerenciar o BD e só deve ser chamado quando o repositório for realmente usado.
*/

@Service
public class ProdutoService {
	
	//os métodos possuem os mesmos nomes aqui que possuem no repositório pois eles só estão sendo praticamente rechamados aqui pra funcionar da mesma forma que no repositório(com exceção de caso existir alguma regra de negócio no método, mas mesmo assim ele pode continuar com o mesmo nome) 
	
	@Autowired//O que essa anotação faz é dizer pro Spring me "devolver o controle" do repositório, no caso ele cria automaticamente uma instancia do objeto em questão (repositório do projeto nesse caso), então eu já posso usar os métodos da classe dona do objeto
	private IProdutoRepository produtoRepository;//Aqui é a instãncia do objeto do repositório, no caso essa linha indica qual a classe o spring deve instanciar pra poder usar os métodos presentes nela, e então ele faz isso automaticamente por causa do @autowired, sendo assim não é necessário criar uma instancia usando o new como é feito normalmente 
	
	public List<ProdutoDTO> obterTodos(){
		
		//Retorna uma lista de produto model (Os que forem encontrados no BD). O .findAll() é um método pertencente ao Jpa, ele retorna uma lista.
		List<Produto> produtos = produtoRepository.findAll();
		
		return produtos.stream()//Varre a lista de produtos
				.map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))//Pega cada um dos produto models  da lista e transforma cada um em um objeto DTO através do ModelMapper que pega cada uma das propriedades do model e copia para o DTO, ele faz isso verificando os nomes de cada propriedade, se for igual, ele passa de um pro outro
				.collect(Collectors.toList());//Vai fazendo uma lista com os produtosDTO que foram criados.
		
		//return produtoRepository.obterTodos();//Aqui é um dos lugares em que a instancia criada pelo spring está sendo usada pra acessar o repositório
	}
	
	public Optional<ProdutoDTO> obterPorId(Integer id){
		Optional<Produto> produto = produtoRepository.findById(id);//Método do JPA que busca o produto pelo id dele no BD. No caso o id do método é o parâmetro de id colocado na entidade da tabela.
		
		if(produto.isEmpty()) {
			throw new ResourceNotFoundException("Produto com id " + id + " não foi encontrado");
		}
		
		//Converte o Optional de produto para um ProdutoDto
		ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);//O .get() usado aqui é um método do Optional que permite pegar o objeto que está dentro do Optional, pois é ele que se precisa pra criar o Dto e seguir em frente
		
		//Converte o produtoDto em um Optional e então o retorna pra função
		return Optional.of(dto);
	}
	
	public ProdutoDTO adicionar(ProdutoDTO produtoDto) {
		produtoDto.setId(null);
		
		ModelMapper modelMapper = new ModelMapper();
		
		Produto produto = modelMapper.map(produtoDto, Produto.class);//Converte o objeto ProdutoDto para Produto com todos os seus atributos
		
		produto = produtoRepository.save(produto);//Método do JPA que salva o objeto no BD
		
		produtoDto.setId(produto.getId());//Pega o id do produto criado no banco e o insere no ProdutoDto para retornar ao cliente
		
		return produtoDto;
	}
	
	public void deletar(Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if (produto.isEmpty()) {
			throw new ResourceNotFoundException("Produto com id " + id + " não foi encontrado");
		}
		
		produtoRepository.deleteById(id);
	}
	
	public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto) {
		produtoDto.setId(id);//Insere o id do produto que será atualizado no DTO pra ele ficar poder ser repassado para o método do JPA que atualizará o produto
		
		ModelMapper mapper = new ModelMapper();
		
		Produto produto = mapper.map(produtoDto, Produto.class);
		
		produtoRepository.save(produto);//O método save() do JPA funciona tanto pra salvar um item novo quanto pra atualizar um já existente, caso o objeto possua um id, ele atualiza o item com o mesmo id no banco, caso não tenha, então ele cria um novo.
		
		return produtoDto;
	}
}
