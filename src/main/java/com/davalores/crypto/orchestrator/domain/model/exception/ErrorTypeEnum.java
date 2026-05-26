package com.davalores.crypto.orchestrator.domain.model.exception;

public enum ErrorTypeEnum {
	
	REPOSITORY_ERROR("REPOSITORY_ERROR"),
	LOGIN_ERROR("LOGIN_ERROR"),
	LOGIN_DFA_ERROR("LOGIN_DFA_ERROR"),
	BUSINESS_ERROR("BUSINESS_ERROR"),
	CUENTA_ERROR("CUENTA_ERROR"),
	OPERATION_ERROR("OPERATION_ERROR"),
	CLIENTE_ERROR("CLIENTE_ERROR"),
	QUOTE_ERROR("QUOTE_ERROR")
    ;
 
    private String type;
 
    ErrorTypeEnum(String type) {
        this.type = type;
    }
 
    public String getType() {
        return type;
    }
}
