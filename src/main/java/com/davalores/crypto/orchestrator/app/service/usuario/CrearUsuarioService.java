package com.davalores.crypto.orchestrator.app.service.usuario;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.usuario.CrearUsuarioPortIn;
import com.davalores.crypto.orchestrator.app.port.out.EncriptadorClaveServicePortOut;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrearUsuarioService implements CrearUsuarioPortIn {

	private final EncriptadorClaveServicePortOut encriptador;
	private final UsuarioRepositoryPortOut repository;
	
	public CrearUsuarioService(EncriptadorClaveServicePortOut encriptador, UsuarioRepositoryPortOut repository) {
		this.encriptador = encriptador;
		this.repository = repository;
	}
	
	@Override
	public Usuario run(Usuario registro) {
		log.debug("inputParam -> {}", registro);
		
		// TODO: validar clave, atributos, etc...
		
		if ( registro.getClave() == null || registro.getClave().isEmpty() ) {
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "La clave no puede ser nula o vacía");
		}
		
		if ( registro.getDescripcion() == null || registro.getDescripcion().isEmpty() ) {
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "La descripcion no puede ser nula o vacía");
		}
		
		if ( registro.getUsuario() == null || registro.getUsuario().isEmpty() ) {
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "El usuario no puede ser nula o vacía");
		}
		
		String claveEncriptada = encriptador.run(registro.getClave());
		registro.setClave(claveEncriptada);
		registro.setDfa(false);
		registro.setHabilitado(true);
		registro.setRipioHabilitado(false);
		
		
		registro = repository.save(registro);
		
		log.debug("outputParam -> {}", registro);
		return registro;
	}

}
