package com.example.circular.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.circular.model.Circular;

public interface CircularRepository extends JpaRepository<Circular, Long> {

Page<Circular> findAll(Specification<Circular> spec, Pageable pageable);
	
	@Query("SELECT o FROM Circular o WHERE o.status = true and o.titulo = ?1")
	List<Circular> findAll(String titulo);	

	@Query("SELECT o FROM Circular o WHERE o.status = true and o.tipoDestinatario.id = ?1")
	List<Circular> findAllTipoDestinatario(Long id);	
	
}
