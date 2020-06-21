package com.carlos.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.cursomc.domain.Categoria;
import com.carlos.cursomc.repositories.CategoriaRepository;
import com.carlos.cursomc.services.exceptions.ObjectNotFoundException;



@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository repo;
	
	public Categoria find(Integer id)  {
		Optional<Categoria> c = repo.findById(id);
		return c.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
}
