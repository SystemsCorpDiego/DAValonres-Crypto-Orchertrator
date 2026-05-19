package com.davalores.crypto.orchestrator.app.port.out;

public interface EncriptadorClaveServicePortOut {

    String run(String rawPassword);

    boolean validar(String password, String password1);

}
