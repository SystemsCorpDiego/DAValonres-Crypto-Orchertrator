package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCoreEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.RepositoryException;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.UsuarioEntity;
import com.davalores.crypto.orchestrator.infra.adapter.out.mapper.UsuarioEntityMapper;
import com.davalores.crypto.orchestrator.infra.adapter.out.repository.UsuarioJpaRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
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
		try {
			Optional<UsuarioEntity> reg = repository.findById(id);
			
			Usuario usuarioBo = null;
			if ( reg.isPresent() )
				usuarioBo = mapper.run(reg.get());
			
			return Optional.ofNullable(usuarioBo);
		} catch (Exception e) {
			log.error("Error al consultar el usuario", e);			
			throw new RepositoryException(ErrorCoreEnum.JPA_ERROR.toString(), "Error al consultar el usuario", e);
		}
	}

	@Override
	public Optional<Usuario> getByUsuario(String usuaDescrip) {
		try {
			Optional<UsuarioEntity> reg = repository.findByUsuario(usuaDescrip);
			Usuario usuario = null;
			if ( reg.isPresent() )
				usuario = mapper.run(reg.get());
			
			return Optional.ofNullable(usuario);
		} catch (Exception e) {
			log.error("Error al consultar el usuario", e);			
			throw new RepositoryException(ErrorCoreEnum.JPA_ERROR.toString(), "Error al consultar el usuario", e);
		}
	}

	@Override
	public Usuario save(Usuario usuario) {
		try {
			UsuarioEntity reg = mapper.run(usuario);
			reg = repository.save(reg);		
			usuario = mapper.run(reg);
			return usuario;
		} catch (Exception e) {
			log.error("Error al guardar el usuario", e);			
			throw new RepositoryException(ErrorCoreEnum.JPA_ERROR.toString(), "Error al guardar el usuario", e);
		}
	}

	@Override
	public Usuario saveDfaSemilla(Integer id, String dfaSemilla) {		
		try {
			UsuarioEntity reg = repository.getReferenceById(id);
			reg = saveDfaSemilla(reg, dfaSemilla);
			return mapper.run(reg);
		} catch (Exception e) {
			log.error("Error al guardar el usuario", e);			
			throw new RepositoryException(ErrorCoreEnum.JPA_ERROR.toString(), "Error al guardar la semilla DFA del usuario", e);
		}

	}

	private UsuarioEntity saveDfaSemilla(UsuarioEntity reg, String dfaSemilla) {
		reg.setDfaSemilla(dfaSemilla);
		reg = repository.save(reg);
		return reg;
	}
	
	@Override
	public Usuario saveDfaSemilla(Usuario usuario, String dfaSemilla) {
		try {
			UsuarioEntity reg = mapper.run(usuario);
			reg = saveDfaSemilla(reg, dfaSemilla);
			
			usuario = mapper.run(reg);
			return usuario;		
		} catch (Exception e) {
			log.error("Error al guardar el usuario", e);			
			throw new RepositoryException(ErrorCoreEnum.JPA_ERROR.toString(), "Error al guardar la semilla DFA del usuario", e);
		}
	}

	@Override
	public Usuario saveConfirmarDfa(Usuario usuario, boolean habilitar) {
		try {
			UsuarioEntity reg = mapper.run(usuario);
			
			reg = saveConfirmarDfa(reg, habilitar);
			usuario = mapper.run(reg);
			
			return usuario;
		} catch (Exception e) {
			log.error("Error al guardar el usuario", e);			
			throw new RepositoryException(ErrorCoreEnum.JPA_ERROR.toString(), "Error al guardar la semilla DFA del usuario", e);
		}
	}
	
	private UsuarioEntity saveConfirmarDfa(UsuarioEntity reg, boolean habilitar) {
		reg.setDfa(habilitar);
		if( !habilitar) {
			reg.setDfaSemilla(null);			
		} else {
			if (reg.getDfaSemilla() == null)
				throw new BusinessException(ErrorCoreEnum.CONFIGURATION_ERROR.toString(), "No se puede habilitar el DFA sin una semilla generada");
		}
		reg = repository.save(reg);
		return reg;
	}

	@Override
	public Usuario saveConfirmarDfa(Integer id, boolean habilitar) {
		try {
			UsuarioEntity reg = repository.getReferenceById(id);
			reg = saveConfirmarDfa(reg, habilitar);		
			
			Usuario usuario = mapper.run(reg);		
			return usuario;
		} catch (Exception e) {
			log.error("Error al guardar el usuario", e);			
			throw new RepositoryException(ErrorCoreEnum.JPA_ERROR.toString(), "Error al guardar la semilla DFA del usuario", e);
		}
	}
		
	
	@Override
	public Usuario saveRipioId(Usuario registro, String ripioId) {
		try {
			UsuarioEntity reg = mapper.run(registro);
			reg.setRipioId(ripioId);
			reg = repository.save(reg);
	
			registro = mapper.run(reg);
			return registro;
		} catch (Exception e) {
			log.error("Error al guardar el usuario", e);			
			throw new RepositoryException(ErrorCoreEnum.JPA_ERROR.toString(), "Error al guardar el id de Cliente Ripio", e);
		}
	}
}
