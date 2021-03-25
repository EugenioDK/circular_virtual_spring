package com.example.circular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.circular.model.TipoCircular;
import com.example.circular.repository.TipoCircularRepository;
import com.example.circular.service.IGenericService;

/**
 * @author Knoll Eugenio
 *
 */
@Service
public class TipoCircularServiceImpl implements IGenericService<TipoCircular, Long> {
	@Autowired
	private TipoCircularRepository tipoCircularRepository;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public TipoCircular find(Long id) throws Exception {
		TipoCircular entityAux = this.tipoCircularRepository.findById(id).get();
		return entityAux;
	}

	@Override
	public Page<TipoCircular> findAll(Specification<TipoCircular> spec, Pageable pageable) throws Exception {
		Page<TipoCircular> lstEntityAux = this.tipoCircularRepository.findAll(spec, pageable);
		return lstEntityAux;
	}

	@Override
	public TipoCircular generate(TipoCircular entity) throws Exception {
		TipoCircular entityAux;
		List<TipoCircular> lstEntityAux = this.tipoCircularRepository.findAll(entity.getNombre().toUpperCase());
		if(lstEntityAux.isEmpty()) {

			entity.setStatus(true);
			entityAux = this.tipoCircularRepository.save(entity);
		} else {
			throw new Exception("Duplicate resource. Generate error");
		}							
		return entityAux;
	}

	@Override
	public TipoCircular amend(Long id, TipoCircular entity) throws Exception {
		TipoCircular entityAux = this.tipoCircularRepository.findById(id).get();
		if (entityAux != null) {
			
			this.tipoCircularRepository.save(entityAux);
			this.setMessage("Modified resource");
		} else {
			throw new Exception("Resource not found. Amend error");
		}
		return entityAux;
	}

	@Override
	public String destroy(Long id) throws Exception {
		TipoCircular entityAux = this.tipoCircularRepository.findById(id).get();
		if(entityAux != null) {
			entityAux.setStatus(false);

			this.tipoCircularRepository.save(entityAux);
			this.setMessage("Resource destroyed");
		}else {
			throw new Exception("Resource not found. Destroy error");
		}
		return this.getMessage();
	}

}