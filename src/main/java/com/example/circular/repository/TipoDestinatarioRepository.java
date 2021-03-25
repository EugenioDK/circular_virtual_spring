package com.example.circular.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.circular.model.TipoDestinatario;

public interface TipoDestinatarioRepository extends JpaRepository<TipoDestinatario, Long> {

Page<TipoDestinatario> findAll(Specification<TipoDestinatario> spec, Pageable pageable);
	
	@Query("SELECT o FROM TipoDestinatario o WHERE o.status = true and o.nombre = ?1")
	List<TipoDestinatario> findAll(String nombre);	

	
}
