package com.davalores.crypto.orchestrator.domain.model.exception;

public enum ErrorTypeEnum {
	
	LOGIN_ERROR("LOGIN_ERROR"),
	BUSINESS_ERROR("BUSINESS_ERROR")
    ;
 
    private String type;
 
    ErrorTypeEnum(String type) {
        this.type = type;
    }
 
    public String getType() {
        return type;
    }
}
