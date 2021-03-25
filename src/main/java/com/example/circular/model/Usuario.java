package com.example.circular.model;

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

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="usuarios")
@Where(clause = "status = true")
public class Usuario implements Serializable{

  
	private static final long serialVersionUID = 94215694497959350L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern="dd-MM-yyyy")
	@Column (name="fecha", columnDefinition = "DATE")
    private Date ultimoLogin;
    private String usuario;
    private String contraseña;
	private String nombreApellido;
	private String dni;
	private String domicilio;
	private String correo;
	@OneToOne
    @JoinColumn(name = "tipo_destinatario_id", referencedColumnName = "id")
	private TipoDestinatario tipoDestinatario;
	private boolean status;
	
    public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getNombreApellido() {
		return nombreApellido;
	}

	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public TipoDestinatario getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestintario(TipoDestinatario tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	
}
