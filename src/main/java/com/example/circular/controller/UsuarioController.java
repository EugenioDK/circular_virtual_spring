package com.example.circular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.circular.model.Usuario;
import com.example.circular.service.UsuarioServiceImpl;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

/**
 * @author Knoll Eugenio
 *
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@RequestMapping(value = "/see/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> find(@PathVariable Long id) {		
		try {
			Usuario entityAux = this.usuarioService.find(id);
			if(entityAux != null) {
				return new ResponseEntity<>(entityAux, HttpStatus.OK);				
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);				
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/see_all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> findAll(@Or({ @Spec(path = "nombreApellido", spec = LikeIgnoreCase.class) }) Specification<Usuario> spec,
			@PageableDefault(page = 0, size = 10, sort = "nombreApellido", direction = Direction.ASC) Pageable pageable) {
		try {
			Page<Usuario> lstAux = this.usuarioService.findAll(spec, pageable);
			return new ResponseEntity<>(lstAux, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
		}			
	}
	
	@RequestMapping(value = "/generate", method = RequestMethod.POST, produces="application/json; charset=UTF-8")
	public ResponseEntity<?> generate(@RequestBody Usuario entity) {
		try {
			Usuario entityAux = this.usuarioService.generate(entity);	
			return new ResponseEntity<>(entityAux, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
		}		
	}
	
	@RequestMapping(value = "/amend/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> amend(@PathVariable Long id, @RequestBody Usuario entityUpdate) {		
		try {
			Usuario entityAux = this.usuarioService.amend(id, entityUpdate);	
			return new ResponseEntity<>(entityAux, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
	}
	
	@RequestMapping(value = "/destroy/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> destroy(@PathVariable Long id) {
		String message;
		try {
			message = this.usuarioService.destroy(id);	
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
	}

}
