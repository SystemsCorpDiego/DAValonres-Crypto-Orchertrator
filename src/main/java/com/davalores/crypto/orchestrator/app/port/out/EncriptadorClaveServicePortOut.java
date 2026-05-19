package com.davalores.crypto.orchestrator.app.port.out;

public interface EncriptadorClaveServicePortOut {

    String run(String clave);

    boolean validar(String clave, String claveEncriptada);

}
