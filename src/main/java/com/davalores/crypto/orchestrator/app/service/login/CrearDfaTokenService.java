package com.davalores.crypto.orchestrator.app.service.login;

import java.security.SecureRandom;
import java.util.Optional;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.login.CrearDfaTokenPortIn;
import com.davalores.crypto.orchestrator.app.port.out.QrCodeServicePortOut;
import com.davalores.crypto.orchestrator.domain.model.DfaToken;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.out.CypherServiceAdapterOut;
import com.davalores.crypto.orchestrator.infra.adapter.out.UsuarioJpaRepositoryAdapterOut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrearDfaTokenService implements CrearDfaTokenPortIn {

	private final String issuer;
	private final CypherServiceAdapterOut cypherService;
	private final QrCodeServicePortOut qrCodeService;
	private final UsuarioJpaRepositoryAdapterOut usuarioRepository;
	
	public CrearDfaTokenService(CypherServiceAdapterOut cypherService, 
			UsuarioJpaRepositoryAdapterOut usuarioRepository, 
			@Value("${login.dfa.issuer:DAV}") String issuer, QrCodeServicePortOut qrCodeService) {
		this.cypherService = cypherService;
		this.qrCodeService = qrCodeService;
		this.usuarioRepository = usuarioRepository;
		this.issuer = issuer;
	}
	
	@Override
	public DfaToken run(Integer idUsuario) {

		//validar Usuario !!
		Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
		if ( usuario.isEmpty() )
			throw new RuntimeException("Usuario no encontrado");

		String secretKey = generateSecretKey();
		String secretKeyCyphed = cypherService.encriptar(secretKey);		
		usuarioRepository.saveDfaSemilla(usuario.get(), secretKeyCyphed);
		
		DfaToken dfaToken = new DfaToken();
		dfaToken.setAccount(usuario.get().getUsuario());
		dfaToken.setSharedSecret(secretKey);
		dfaToken.setIssuer(issuer);
		try {
			dfaToken.setQrCodeImg( qrCodeService.generarBase64(dfaToken.getAuthenticatorBarCode(), 300, 300) );
		} catch (Exception e) {
			log.error("ERROR qrCodeService.generar - e: {}", e.getMessage());
		}
		
		return dfaToken;
	}

	private String generateSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes);
	}
}
