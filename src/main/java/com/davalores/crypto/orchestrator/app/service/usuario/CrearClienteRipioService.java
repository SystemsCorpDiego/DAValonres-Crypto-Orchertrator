package com.davalores.crypto.orchestrator.app.service.usuario;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.usuario.CrearClienteRipioPortIn;
import com.davalores.crypto.orchestrator.app.port.out.CrearClienteRipioPortOut;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.ClienteRipio;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;

@Service
public class CrearClienteRipioService implements CrearClienteRipioPortIn {

	private final UsuarioRepositoryPortOut repository;
	private final CrearClienteRipioPortOut crearClienteRipioPortOut;
	
	public CrearClienteRipioService(UsuarioRepositoryPortOut repository, CrearClienteRipioPortOut crearClienteRipioPortOut) {
		this.repository = repository;
		this.crearClienteRipioPortOut = crearClienteRipioPortOut; 
	}
	
	@Override
	public void run(Integer usuarioId) {
		// TODO traer usuario, validar q no tenga ripioId, crear cliente ripio, guardar ripioId en usuario
		
		Optional<Usuario> usuario = repository.findById(usuarioId);		
		if (usuario.isEmpty())
			throw new BusinessException(ErrorCodeEnum.BUSINESS_ERROR.toString(), "Usuario no encontrado para o id: " + usuarioId);

		if (usuario.get().getRipioId() != null) 
			throw new BusinessException(ErrorCodeEnum.BUSINESS_ERROR.toString(),  "Usuario ya tiene id de Cliente Ripio");
		
		ClienteRipio clienteRipio = crearClienteRipioPortOut.run();
		
		repository.saveRipioId(usuario.get(), clienteRipio.getId());
		
		
	}

}
