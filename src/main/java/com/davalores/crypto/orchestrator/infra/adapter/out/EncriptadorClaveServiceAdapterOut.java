package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.EncriptadorClaveServicePortOut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EncriptadorClaveServiceAdapterOut implements EncriptadorClaveServicePortOut {

	//Implementacion de Algoritmo BCrypt para cifrar contraseñas de manera unidireccional
	private final BCryptPasswordEncoder encoder;

    public EncriptadorClaveServiceAdapterOut() {
        this.encoder = new BCryptPasswordEncoder(12); //strength default es 10
    }

    @Override
    public String run(String rawPassword) {
        log.debug("Encode password");
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean validar(String password, String password1) {
        log.debug("Match passwords");
        return encoder.matches(password, password1);
    }

}
