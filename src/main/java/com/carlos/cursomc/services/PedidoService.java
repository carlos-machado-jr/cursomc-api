package com.carlos.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.cursomc.domain.Pedido;
import com.carlos.cursomc.repositories.PedidoRepository;
import com.carlos.cursomc.services.exceptions.ObjectNotFoundException;



@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository repo;
	
	public Pedido find(Integer id)  {
		Optional<Pedido> c = repo.findById(id);
		return c.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
	}
}
