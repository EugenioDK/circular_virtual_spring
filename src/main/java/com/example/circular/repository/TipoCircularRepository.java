package com.example.circular.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.circular.model.TipoCircular;

public interface TipoCircularRepository extends JpaRepository<TipoCircular, Long> {

Page<TipoCircular> findAll(Specification<TipoCircular> spec, Pageable pageable);
	
	@Query("SELECT o FROM TipoCircular o WHERE o.status = true and o.nombre = ?1")
	List<TipoCircular> findAll(String titulo);	

}
