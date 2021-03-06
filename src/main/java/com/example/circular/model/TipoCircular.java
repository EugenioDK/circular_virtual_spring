package com.example.circular.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="tipos_circulares")
@Where(clause = "status = true")
public class TipoCircular implements Serializable{

  
	private static final long serialVersionUID = -8139206361000286651L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private boolean status;

    public TipoCircular() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

