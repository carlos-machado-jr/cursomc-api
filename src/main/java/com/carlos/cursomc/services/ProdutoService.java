package com.carlos.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.carlos.cursomc.domain.Categoria;
import com.carlos.cursomc.domain.Produto;
import com.carlos.cursomc.repositories.CategoriaRepository;
import com.carlos.cursomc.repositories.ProdutoRepository;
import com.carlos.cursomc.services.exceptions.ObjectNotFoundException;



@Service
public class ProdutoService {
	
	@Autowired
	ProdutoRepository repo;
	
	@Autowired
	CategoriaRepository catRepo;
	
	public Produto find(Integer id)  {
		Optional<Produto> c = repo.findById(id);
		return c.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Produto.class.getName())); 
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		
		List<Categoria> categorias = catRepo.findAllById(ids);
		
		return repo.search(nome, categorias, pageRequest);
		
	}
}
