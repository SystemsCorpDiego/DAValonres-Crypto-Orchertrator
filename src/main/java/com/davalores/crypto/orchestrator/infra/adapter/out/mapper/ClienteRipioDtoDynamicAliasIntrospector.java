package com.davalores.crypto.orchestrator.infra.adapter.out.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class ClienteRipioDtoDynamicAliasIntrospector extends JacksonAnnotationIntrospector {

	private static final long serialVersionUID = -2360012565847827939L;
	
	//setId es destino, idExterno es origen
	private final Map<String, List<String>> aliasMap = Map.of(
	        "setId", List.of("idExterno")         
	    );
	
	
	@Override
    public List<PropertyName> findPropertyAliases(Annotated a) {
        return aliasMap.getOrDefault(a.getName(), Collections.emptyList())
            .stream().map(PropertyName::new).toList();
    }
}
