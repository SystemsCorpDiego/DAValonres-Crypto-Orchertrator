package com.davalores.crypto.orchestrator.infra.adapter.out.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class LoginEscoDtoDynamicAliasIntrospector extends JacksonAnnotationIntrospector {

	private static final long serialVersionUID = 34600728324292221L;
	private final Map<String, List<String>> aliasMap = Map.of(
	        "setId", List.of("userID"),
	        "setTipo", List.of("userType")	        
	    );
	
	
	@Override
    public List<PropertyName> findPropertyAliases(Annotated a) {
        return aliasMap.getOrDefault(a.getName(), Collections.emptyList())
            .stream().map(PropertyName::new).toList();
    }
	 

}
