package com.example.circular.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="confirmaciones_lectura")
@Where(clause = "status = true")
public class ConfirmacionLectura implements Serializable{

   
	private static final long serialVersionUID = -8139206361000286651L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern="dd-MM-yyyy")
	@Column (name="fecha_lectura", columnDefinition = "DATE")
    private Date fechaLectura;
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
    private boolean status;

    public ConfirmacionLectura() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFechaLectura() {
		return fechaLectura;
	}

	public void setFechaLectura(Date fechaLectura) {
		this.fechaLectura = fechaLectura;
	}

}
