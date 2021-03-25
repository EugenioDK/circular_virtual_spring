package com.example.circular.model;


import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="circulares")
@Where(clause = "status = true")
public class Circular implements Serializable{

	
	private static final long serialVersionUID = 6942057060228658516L;


	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    @JsonFormat(pattern="dd-MM-yyyy")
	@Column (name="fecha", columnDefinition = "DATE")
    private Date fecha;
    private File adjunto;
    @OneToOne
    @JoinColumn(name = "tipo_destinatario_id", referencedColumnName = "id")
    private TipoDestinatario tipoDestinatario;
    @OneToOne
    @JoinColumn(name = "tipo_circular_id", referencedColumnName = "id")
    private TipoCircular tipoCircular;
    @ManyToMany
	@JoinTable(name = "lst_confirmacion_lectura", joinColumns = @JoinColumn(name = "circular_id"), inverseJoinColumns = @JoinColumn(name = "conf_lectura_id"))
    private List<ConfirmacionLectura> lstConfirmacionLectura = new ArrayList<>();

    @Transient
	private boolean confirmado;
    private boolean status;

    public Circular() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripciopn) {
        this.descripcion = descripciopn;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public File getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(File adjunto) {
        this.adjunto = adjunto;
    }

    public TipoDestinatario getTipoDestinatario() {
        return tipoDestinatario;
    }

    public void setTipoDestinatario(TipoDestinatario tipoDestinatario) {
        this.tipoDestinatario = tipoDestinatario;
    }

    public TipoCircular getTipoCircular() {
        return tipoCircular;
    }

    public void setTipoCircular(TipoCircular tipoCircular) {
        this.tipoCircular = tipoCircular;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

	public List<ConfirmacionLectura> getLstConfirmacionLectura() {
		return lstConfirmacionLectura;
	}

	public void setLstConfirmacionLectura(List<ConfirmacionLectura> lstConfirmacionLectura) {
		this.lstConfirmacionLectura = lstConfirmacionLectura;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}
	
	
}
