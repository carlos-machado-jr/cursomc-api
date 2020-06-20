package com.carlos.cursomc.resources;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.cursomc.domain.Categoria;
import com.carlos.cursomc.services.CategoriaService;


@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	
	
		
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> listarPorId(@PathVariable Integer id)  {
		
		Categoria c = service.buscar(id);
		return ResponseEntity.ok(c); 
		
	}
	
	
}
