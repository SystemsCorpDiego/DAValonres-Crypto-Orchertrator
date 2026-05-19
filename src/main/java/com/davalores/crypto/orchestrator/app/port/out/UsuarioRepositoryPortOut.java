package com.davalores.crypto.orchestrator.app.port.out;

import java.util.Optional;

import com.davalores.crypto.orchestrator.domain.model.Usuario;

public interface UsuarioRepositoryPortOut {
	//TODO: definir métodos de salída para o repositório de usuários
	
	public Optional<Usuario> findById(Integer id);
	
	public Optional<Usuario> getByUsuario(String usuario);
	
	public Usuario save(Usuario registro);
	
	public Usuario saveDfaSemilla(Integer id, String dfaSemilla);
	public Usuario saveDfaSemilla(Usuario registro, String dfaSemilla);
	
	public Usuario saveConfirmarDfa(Usuario registro, boolean habilitar);
	public Usuario saveConfirmarDfa(Integer id, boolean habilitar);
	
}
