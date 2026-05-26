package com.davalores.crypto.orchestrator.app.service.usuario;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.usuario.CambioClaveUsuarioPortIn;
import com.davalores.crypto.orchestrator.app.port.out.EncriptadorClaveServicePortOut;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CambioClaveUsuarioDto;

@Service
public class CambioClaveUsuarioService implements CambioClaveUsuarioPortIn {

	private final GetTokenUsuarioService getTokenUsuarioService;
	private final UsuarioRepositoryPortOut portOut;
	private final EncriptadorClaveServicePortOut encriptadorClaveService;
		
	public CambioClaveUsuarioService(UsuarioRepositoryPortOut portOut, 
			GetTokenUsuarioService getTokenUsuarioService,
			EncriptadorClaveServicePortOut encriptadorClaveService) {
		super();
		this.portOut = portOut;
		this.getTokenUsuarioService = getTokenUsuarioService;
		this.encriptadorClaveService = encriptadorClaveService;
	}



	@Override
	public void run(String token, Integer usuarioId, CambioClaveUsuarioDto dto) {
		
		Usuario usuarioLogin = getTokenUsuarioService.run(token, TokenTipoEnum.NORMAL);
		
		if ( !usuarioLogin.getId().equals(usuarioId) ) {
			//TODO: valido Permisos
		}
		
		Optional<Usuario> usuario = portOut.findById(usuarioId);
		if ( usuario.isEmpty() )
			throw new BusinessException(ErrorCodeEnum.NO_DATA_FOUND_ERROR.toString(), "Usuario inexististente");
		if ( usuario.get().getEscoId() != null )
			throw new BusinessException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "No se puede modificar la clave de un Usuario ESCO");
		
		
		if ( !encriptadorClaveService.validar(dto.getClave(), usuario.get().getClave() ) )
			throw new BusinessException(ErrorCodeEnum.BUSINESS_ERROR.toString(), "La clave actual no es correcta");

		String claveNueva = encriptadorClaveService.run(dto.getClaveNueva());
		
		usuario.get().setClave(claveNueva);
				
		portOut.save(usuario.get());				
	}

}
