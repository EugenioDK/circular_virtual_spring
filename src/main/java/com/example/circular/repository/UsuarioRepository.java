package com.example.circular.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.circular.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

Page<Usuario> findAll(Specification<Usuario> spec, Pageable pageable);
	
	@Query("SELECT o FROM Usuario o WHERE o.status = true and o.nombreApellido = ?1")
	List<Usuario> findAll(String nombre);

	@Query("SELECT o FROM Usuario o WHERE o.status = true and o.tipoDestinatario.id = ?1")
	List<Usuario> findAllDestinatario(Long id);
	
	@Query("SELECT o FROM Usuario o WHERE o.status = true and o.usuario = ?1 and o.contraseña = ?2 ")
	List<Usuario> findByUss(String usuario, String contraseña);
	
}
