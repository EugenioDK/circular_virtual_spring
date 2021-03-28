package com.example.circular.controller;

import java.util.List;

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

import com.example.circular.model.Circular;
import com.example.circular.service.CircularServiceImpl;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

/**
 * @author Knoll Eugenio
 *
 */
@RestController
@RequestMapping("/circular")
public class CircularController {
	@Autowired
	private CircularServiceImpl circularService;
	
	@RequestMapping(value = "/see/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> find(@PathVariable Long id) {		
		try {
			Circular entityAux = this.circularService.find(id);
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
	public ResponseEntity<?> findAll(@Or({ @Spec(path = "titulo", spec = LikeIgnoreCase.class) }) Specification<Circular> spec,
			@PageableDefault(page = 0, size = 100, sort = "fecha", direction = Direction.ASC) Pageable pageable) {
		try {
			Page<Circular> lstAux = this.circularService.findAll(spec, pageable);
			return new ResponseEntity<>(lstAux, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
		}			
	}

	@RequestMapping(value = "/see_all_destinatario/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> findAllPorDestinatarioControl(@PathVariable Long id) {
		try {
			List<Circular> lstAux = this.circularService.findAllPorDestinatario(id);
			return new ResponseEntity<>(lstAux, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
		}			
	}
	
	@RequestMapping(value = "/generate", method = RequestMethod.POST, produces="application/json; charset=UTF-8")
	public ResponseEntity<?> generate(@RequestBody Circular entity) {
		try {

			Circular entityAux = this.circularService.generate(entity);	
			return new ResponseEntity<>(entityAux, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
		}		
	}
	
	@RequestMapping(value = "/amend/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> amend(@PathVariable Long id, @RequestBody Circular entityUpdate) {		
		try {
			Circular entityAux = this.circularService.amend(id, entityUpdate);	
			return new ResponseEntity<>(entityAux, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
	}
	
	@RequestMapping(value = "/destroy/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> destroy(@PathVariable Long id) {
		String message;
		try {
			message = this.circularService.destroy(id);	
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
	}
	

	@RequestMapping(value = "/confirmar/{id1}/{id2}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> confirmar(@PathVariable Long id1, @PathVariable Long id2) {		
		String message;
		try {
			message = this.circularService.confirmarLectura(id1, id2);	
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
	}

}
