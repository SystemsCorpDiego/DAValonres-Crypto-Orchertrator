package com.davalores.crypto.orchestrator.app.port.out;

public interface CypherServicePortOut {

	String encrypt(String input);

	String decrypt(String input);

}
