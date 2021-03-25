package com.example.circular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.circular.model.TipoDestinatario;
import com.example.circular.repository.TipoDestinatarioRepository;

/**
 * @author Knoll Eugenio
 *
 */
@Service
public class TipoDestinatarioServiceImpl implements IGenericService<TipoDestinatario, Long> {
	@Autowired
	private TipoDestinatarioRepository tipoDestinatarioRepository;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public TipoDestinatario find(Long id) throws Exception {
		TipoDestinatario entityAux = this.tipoDestinatarioRepository.findById(id).get();
		return entityAux;
	}

	@Override
	public Page<TipoDestinatario> findAll(Specification<TipoDestinatario> spec, Pageable pageable) throws Exception {
		Page<TipoDestinatario> lstEntityAux = this.tipoDestinatarioRepository.findAll(spec, pageable);
		return lstEntityAux;
	}

	@Override
	public TipoDestinatario generate(TipoDestinatario entity) throws Exception {
		TipoDestinatario entityAux;
		List<TipoDestinatario> lstEntityAux = this.tipoDestinatarioRepository.findAll(entity.getNombre().toUpperCase());
		if(lstEntityAux.isEmpty()) {

			entity.setStatus(true);
			entityAux = this.tipoDestinatarioRepository.save(entity);
		} else {
			throw new Exception("Duplicate resource. Generate error");
		}							
		return entityAux;
	}

	@Override
	public TipoDestinatario amend(Long id, TipoDestinatario entity) throws Exception {
		TipoDestinatario entityAux = this.tipoDestinatarioRepository.findById(id).get();
		if (entityAux != null) {
			
			this.tipoDestinatarioRepository.save(entityAux);
			this.setMessage("Modified resource");
		} else {
			throw new Exception("Resource not found. Amend error");
		}
		return entityAux;
	}

	@Override
	public String destroy(Long id) throws Exception {
		TipoDestinatario entityAux = this.tipoDestinatarioRepository.findById(id).get();
		if(entityAux != null) {
			entityAux.setStatus(false);

			this.tipoDestinatarioRepository.save(entityAux);
			this.setMessage("Resource destroyed");
		}else {
			throw new Exception("Resource not found. Destroy error");
		}
		return this.getMessage();
	}

}

