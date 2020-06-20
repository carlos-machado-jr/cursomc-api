package com.carlos.cursomc.resources;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.cursomc.domain.Cliente;
import com.carlos.cursomc.services.ClienteService;


@RestController
@RequestMapping("/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	
	
		
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> listarPorId(@PathVariable Integer id)  {
		
		Cliente c = service.buscar(id);
		return ResponseEntity.ok(c); 
		
	}
	
	
}
