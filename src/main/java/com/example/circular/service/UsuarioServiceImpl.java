package com.example.circular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.circular.model.Usuario;
import com.example.circular.repository.UsuarioRepository;


/**
 * @author Knoll Eugenio
 *
 */
@Service
public class UsuarioServiceImpl implements IGenericService<Usuario, Long> {
	@Autowired
	private UsuarioRepository usuarioRepository;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Usuario find(Long id) throws Exception {
		Usuario entityAux = this.usuarioRepository.findById(id).get();
		return entityAux;
	}

	@Override
	public Page<Usuario> findAll(Specification<Usuario> spec, Pageable pageable) throws Exception {
		Page<Usuario> lstEntityAux = this.usuarioRepository.findAll(spec, pageable);
		return lstEntityAux;
	}

	@Override
	public Usuario generate(Usuario entity) throws Exception {
		Usuario entityAux;
		List<Usuario> lstEntityAux = this.usuarioRepository.findAll(entity.getUsuario().toUpperCase());
		if(lstEntityAux.isEmpty()) {

			entity.setStatus(true);
			entityAux = this.usuarioRepository.save(entity);
		} else {
			throw new Exception("Duplicate resource. Generate error");
		}							
		return entityAux;
	}

	@Override
	public Usuario amend(Long id, Usuario entity) throws Exception {
		Usuario entityAux = this.usuarioRepository.findById(id).get();
		if (entityAux != null) {
			
			this.usuarioRepository.save(entityAux);
			this.setMessage("Modified resource");
		} else {
			throw new Exception("Resource not found. Amend error");
		}
		return entityAux;
	}

	@Override
	public String destroy(Long id) throws Exception {
		Usuario entityAux = this.usuarioRepository.findById(id).get();
		if(entityAux != null) {
			entityAux.setStatus(false);

			this.usuarioRepository.save(entityAux);
			this.setMessage("Resource destroyed");
		}else {
			throw new Exception("Resource not found. Destroy error");
		}
		return this.getMessage();
	}

}

