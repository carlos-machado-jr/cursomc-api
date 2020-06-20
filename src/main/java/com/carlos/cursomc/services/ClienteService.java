package com.carlos.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.cursomc.domain.Cliente;
import com.carlos.cursomc.repositories.ClienteRepository;
import com.carlos.cursomc.services.exceptions.ObjectNotFoundException;



@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository repo;
	
	public Cliente buscar(Integer id)  {
		Optional<Cliente> c = repo.findById(id);
		return c.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
	}
}
