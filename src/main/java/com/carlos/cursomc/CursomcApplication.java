package com.carlos.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carlos.cursomc.domain.Categoria;
import com.carlos.cursomc.domain.Cidade;
import com.carlos.cursomc.domain.Cliente;
import com.carlos.cursomc.domain.Endereco;
import com.carlos.cursomc.domain.Estado;
import com.carlos.cursomc.domain.ItemPedido;
import com.carlos.cursomc.domain.Pagamento;
import com.carlos.cursomc.domain.PagamentoComBoleto;
import com.carlos.cursomc.domain.PagamentoComCartao;
import com.carlos.cursomc.domain.Pedido;
import com.carlos.cursomc.domain.Produto;
import com.carlos.cursomc.domain.enums.EstadoPagamento;
import com.carlos.cursomc.domain.enums.TipoCliente;
import com.carlos.cursomc.repositories.CategoriaRepository;
import com.carlos.cursomc.repositories.CidadeRepository;
import com.carlos.cursomc.repositories.ClienteRepository;
import com.carlos.cursomc.repositories.EnderecoRepository;
import com.carlos.cursomc.repositories.EstadoRepository;
import com.carlos.cursomc.repositories.ItemPedidoRepository;
import com.carlos.cursomc.repositories.PagamentoRepository;
import com.carlos.cursomc.repositories.PedidoRepository;
import com.carlos.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama, mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "Computador", 1200.50);
		Produto p2 = new Produto(null, "Impressora", 835.50);
		Produto p3 = new Produto(null, "Mouse", 20.80);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cl1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "54545454", TipoCliente.PESSOAFISICA);

		cl1.getTelefones().addAll((Arrays.asList("2511115544", "11225544")));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", c1, "Jardim", "38922250", cl1);
		Endereco e2 = new Endereco(null, "Avenida Mattos", "105", "Sala 800", c2, "Centro", "58944250", cl1);

		clienteRepository.save(cl1);
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cl1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cl1, e2);

		cl1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),null);
		ped2.setPagamento(pagto2);
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00 , 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00 , 2, 200.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00 , 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().add(ip3);
		
		p1.getItens().add(ip1);
		p2.getItens().add(ip3);
		p3.getItens().add(ip2);
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}
