package com.davalores.crypto.orchestrator.domain.model;

public enum EPerfil {
	
	ROOT( (short)1, "ROOT"),
	CLIENTE( (short)2, "CLIENTE"),
	ADMINISTRATIVO( (short)3, "ADMINISTRATIVO");
	
	private Short id;
    private String descripcion;
    
    EPerfil(Short id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
	public static EPerfil map(java.lang.Short id) {
        for(EPerfil e : values()) {
            if(e.id.equals(id)) return e;
        }
        return null;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public Short getId() {
        return id;
    }

}
