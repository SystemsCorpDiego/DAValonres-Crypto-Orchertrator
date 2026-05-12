package com.davalores.crypto.orchestrator.app.service.common.jwt;

public enum TokenTipoEnum {

    NORMAL("normal"),
    REFRESH("refresh"),
    AUTENTICACION_PARCIAL("autenticacion_parcial")
    ;
 
    private String url;
 
    TokenTipoEnum(String envUrl) {
        this.url = envUrl;
    }
 
    public String getUrl() {
        return url;
    }
}
