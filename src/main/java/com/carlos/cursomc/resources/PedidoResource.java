package com.carlos.cursomc.resources;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.cursomc.domain.Pedido;
import com.carlos.cursomc.services.PedidoService;


@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	
	
		
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> listarPorId(@PathVariable Integer id)  {
		
		Pedido c = service.buscar(id);
		return ResponseEntity.ok(c); 
		
	}
	
	
}
