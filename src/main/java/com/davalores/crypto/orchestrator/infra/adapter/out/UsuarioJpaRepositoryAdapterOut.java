package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.UsuarioEntity;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.UsuarioEntityMapper;

@Repository 
public class UsuarioJpaRepositoryAdapterOut implements UsuarioRepositoryPortOut {

	private final UsuarioJpaRepository repository;
	private final UsuarioEntityMapper mapper;
	
	public UsuarioJpaRepositoryAdapterOut(UsuarioJpaRepository repository, UsuarioEntityMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	@Override
	public Optional<Usuario> findById(Integer id) {

		Optional<UsuarioEntity> reg = repository.findById(id);
		
		Usuario usuarioBo = null;
		if ( reg.isPresent() )
			usuarioBo = mapper.run(reg.get());
		
		return Optional.of(usuarioBo);
	}

	@Override
	public Optional<Usuario> getByUsuario(String usuaDescrip) {
		
		Optional<UsuarioEntity> reg = repository.findByUsuario(usuaDescrip);
		Usuario usuario = null;
		if ( reg.isPresent() )
			usuario = mapper.run(reg.get());
		
		return Optional.of(usuario);
	}

	@Override
	public Usuario save(Usuario usuario) {
		UsuarioEntity reg = mapper.run(usuario);
		reg = repository.save(reg);		
		usuario = mapper.run(reg);
		return usuario;
	}

	@Override
	public Usuario saveDfaSemilla(Integer id, String dfaSemilla) {
		// TODO Auto-generated method stub
		UsuarioEntity reg = repository.getReferenceById(id);
		reg = saveDfaSemilla(reg, dfaSemilla);
		return mapper.run(reg);
	}

	private UsuarioEntity saveDfaSemilla(UsuarioEntity reg, String dfaSemilla) {
		reg.setDfaSemilla(dfaSemilla);
		reg = repository.save(reg);
		return reg;
	}
	
	@Override
	public Usuario saveDfaSemilla(Usuario usuario, String dfaSemilla) {
		UsuarioEntity reg = mapper.run(usuario);
		reg = saveDfaSemilla(reg, dfaSemilla);
		
		usuario = mapper.run(reg);
		return usuario;		
	}

	@Override
	public Usuario saveConfirmarDfa(Usuario usuario, boolean habilitar) {
		// TODO Auto-generated method stub
		UsuarioEntity reg = mapper.run(usuario);
		
		reg = saveConfirmarDfa(reg, habilitar);
		usuario = mapper.run(reg);
		
		return usuario;
	}
	
	private UsuarioEntity saveConfirmarDfa(UsuarioEntity reg, boolean habilitar) {
		reg.setDfa(habilitar);
		if( !habilitar) {
			reg.setDfaSemilla(null);			
		} else {
			if (reg.getDfaSemilla() == null)
				throw new BusinessException("No se puede habilitar el DFA sin una semilla generada");
		}
		reg = repository.save(reg);
		return reg;
	}

	@Override
	public Usuario saveConfirmarDfa(Integer id, boolean habilitar) {
		
		UsuarioEntity reg = repository.getReferenceById(id);
		reg = saveConfirmarDfa(reg, habilitar);		
		
		Usuario usuario = mapper.run(reg);		
		return usuario;
	}
		
}
