package com.davalores.crypto.orchestrator.app.service.common.jwt;

public enum ETokenTipo {
    NORMAL("normal"),
    REFRESH("refresh"),
    VERIFICACION("verificacion"),
    AUTENTICACION_PARCIAL("autenticacion_parcial")
    ;
 
    private String url;
 
    ETokenTipo(String envUrl) {
        this.url = envUrl;
    }
 
    public String getUrl() {
        return url;
    }

}
