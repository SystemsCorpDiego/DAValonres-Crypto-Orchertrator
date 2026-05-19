package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;

@Repository
public class UsuarioJpaRepositoryAdapterOut implements UsuarioRepositoryPortOut {

	private final UsuarioJpaRepository repository;
	
	public UsuarioJpaRepositoryAdapterOut(UsuarioJpaRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Optional<Usuario> findById(Integer id) {
		
		Optional<Usuario> usuario = repository.findById(id);
		
		return usuario;
	}

	@Override
	public Optional<Usuario> getByUsuario(String usuaDescrip) {
		
		Optional<Usuario> usuario = repository.findByUsuario(usuaDescrip);
		return usuario;
	}

	@Override
	public Usuario save(Usuario registro) {

		Usuario usuario = repository.save(registro);		
		return usuario;
	}

	@Override
	public Usuario saveDfaSemilla(Integer id, String dfaSemilla) {
		// TODO Auto-generated method stub
		Usuario usuario = repository.getReferenceById(id);
		return saveDfaSemilla(usuario, dfaSemilla);
	}

	@Override
	public Usuario saveDfaSemilla(Usuario registro, String dfaSemilla) {
		registro.setDfaSemilla(dfaSemilla);
		registro = repository.save(registro);
		return registro;		
	}

	@Override
	public Usuario saveConfirmarDfa(Usuario registro, boolean habilitar) {
		// TODO Auto-generated method stub
		registro.setDfa(habilitar);
		if( !habilitar) {
			registro.setDfaSemilla(null);			
		} else {
			if (registro.getDfaSemilla() == null)
				throw new BusinessException("No se puede habilitar el DFA sin una semilla generada");
		}
		
		registro = repository.save(registro);
		
		return registro;
	}

	@Override
	public Usuario saveConfirmarDfa(Integer id, boolean habilitar) {
		Usuario usuario = repository.getReferenceById(id);
		usuario = saveConfirmarDfa(usuario, habilitar);		
		return usuario;
	}
	
	
}
