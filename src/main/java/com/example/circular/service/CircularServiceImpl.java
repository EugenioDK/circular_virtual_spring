package com.example.circular.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.circular.model.Circular;
import com.example.circular.model.ConfirmacionLectura;
import com.example.circular.model.Usuario;
import com.example.circular.repository.CircularRepository;
import com.example.circular.repository.ConfirmacionLecturaRepository;
import com.example.circular.repository.UsuarioRepository;

import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Knoll Eugenio
 *
 */
@Service
public class CircularServiceImpl implements IGenericService<Circular, Long> {
	@Autowired
	private CircularRepository circularRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ConfirmacionLecturaRepository ConfirmacionRepository;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Circular find(Long id) throws Exception {
		Circular entityAux = this.circularRepository.findById(id).get();
		return entityAux;
	}

	
	public List<Circular> findAllPorDestinatario(Long idUs) throws Exception {
		Usuario usuarioAux = this.usuarioRepository.findById(idUs).get();
		System.out.println("#### USUARIO " + usuarioAux.getCorreo());
		
		List<Circular> lstCirculares = this.circularRepository.findAllTipoDestinatario(usuarioAux .getTipoDestinatario().getId());
		List<Circular> lstCircularesFinal = new ArrayList<>();
		
		for(Circular circularAux: lstCirculares) {
			circularAux.setConfirmado(false);
			for(ConfirmacionLectura confLec : circularAux.getLstConfirmacionLectura()) {
				
				if(confLec.getUsuario() == usuarioAux) {
					circularAux.setConfirmado(true);
				}
			}
			lstCircularesFinal.add(circularAux);
		}
		
		return lstCircularesFinal;
	}
	
	@Override
	public Page<Circular> findAll(Specification<Circular> spec, Pageable pageable) throws Exception {
		Page<Circular> lstEntityAux = this.circularRepository.findAll(spec, pageable);
		return lstEntityAux;
	}

	@Override
	public Circular generate(Circular entity) throws Exception {
		Circular entityAux;
		List<Circular> lstEntityAux = this.circularRepository.findAll(entity.getTitulo().toUpperCase());
		if(lstEntityAux.isEmpty()) {

			entity.setStatus(true);
			entity.setFecha(new Date());
			entityAux = this.circularRepository.save(entity);
			
			sendEMail(entity);
		} else {
			throw new Exception("Duplicate resource. Generate error");
		}							
		return entityAux;
	}

	@Override
	public Circular amend(Long id, Circular entity) throws Exception {
		Circular entityAux = this.circularRepository.findById(id).get();
		if (entityAux != null) {
			Circular circular = this.circularRepository.getOne(id);
			circular.setTitulo(entity.getTitulo());
			circular.setDescripcion(entity.getDescripcion());
			
			this.circularRepository.save(entityAux);
			this.setMessage("Modified resource");
		} else {
			throw new Exception("Resource not found. Amend error");
		}
		return entityAux;
	}

	@Override
	public String destroy(Long id) throws Exception {
		Circular entityAux = this.circularRepository.findById(id).get();
		if(entityAux != null) {
			entityAux.setStatus(false);

			this.circularRepository.save(entityAux);
			this.setMessage("Resource destroyed");
		}else {
			throw new Exception("Resource not found. Destroy error");
		}
		return this.getMessage();
	}
	

	public String confirmarLectura(Long idCircular, Long idUsuario) throws Exception {
		Circular circular = this.circularRepository.findById(idCircular).get();
		if (circular != null) {
			
			Usuario usuario = this.usuarioRepository.getOne(idUsuario);
			
			ConfirmacionLectura confLec = new ConfirmacionLectura();
			confLec.setFechaLectura(new Date());
			confLec.setStatus(true);
			confLec.setUsuario(usuario);
			
			
			this.ConfirmacionRepository.save(confLec);
			
			List<ConfirmacionLectura> lstConfLecAux = new ArrayList<>();
			lstConfLecAux= circular.getLstConfirmacionLectura();
			
			lstConfLecAux.add(confLec);
			
			circular.setLstConfirmacionLectura(lstConfLecAux);
			
			
			this.circularRepository.save(circular);
			this.setMessage("Confirmacion guardada");
		} else {
			throw new Exception("Resource not found. Amend error");
		}
		return this.getMessage();
	}
	
	@Async
	public void sendEMail(Circular circular) throws Exception {
		try {
			List<Usuario> usuarios = this.usuarioRepository.findAllDestinatario(circular.getTipoDestinatario().getId());
			
			Properties propertieAux = new Properties();
			propertieAux.setProperty("mail.smtp.host", "smtp.gmail.com");
			propertieAux.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
			propertieAux.setProperty("mail.smtp.starttls.enable", "true");
			propertieAux.setProperty("mail.smtp.port", "587");
			propertieAux.setProperty("mail.smtp.user", "eugenioknoll@gmail.com"); // ACA VA El Mail
			propertieAux.setProperty("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(propertieAux);

			Transport t = session.getTransport("smtp");
			t.connect("eugenioknoll@gmail.com", "keugenio1992"); // ACA VA EL MAIL Y PASS
			if (!usuarios.isEmpty()) {
				for (Usuario usuarioAux : usuarios) {
					MimeMessage message = new MimeMessage(session);
					message.addHeader("Disposition-Notification-To", "eugenioknoll@gmail.com"); // ACA TAMBIEN
																											// VA EL
																											// MAIL
					message.setFrom(new InternetAddress("eugenioknoll@gmail.com")); // ACA TAMBIEN VA EL MAIL
					message.addRecipient(Message.RecipientType.TO,
							new InternetAddress(usuarioAux.getCorreo()));
					message.setSubject("Notificación de Nueva Circular N° " + circular.getId());
					message.setText(
							"|| NUEVA CIRCULAR || \r\n"
									+ "NUMERO: " + circular.getId() + ".\r\n"
									+ "TITULO: " + circular.getTitulo().toUpperCase() + ".\r\n"
									+"\r\n"
									+ "Ingrese al sistema de CircularVirtual para más detalles.");
					t.sendMessage(message, message.getAllRecipients());
				}
			}
		
			t.close();
		} catch (NoSuchProviderException e) {
			throw new Exception("No se pudo envial el mail. " + e.getMessage() + " / " + e.toString());
		}
	}


}