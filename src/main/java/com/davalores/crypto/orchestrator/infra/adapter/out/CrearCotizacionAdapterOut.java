package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.orchestrator.app.port.out.CrearCotizacionPortOut;
import com.davalores.crypto.orchestrator.domain.model.Cotizacion;
import com.davalores.crypto.orchestrator.domain.model.CotizacionSolicitud;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrearCotizacionAdapterOut implements CrearCotizacionPortOut {

	private final String protocolo; 
	private final String dominio; 
	private final String apiPath; 
	
	public CrearCotizacionAdapterOut(
			@Value("${apis.crypto-provider.ripio.protocolo}") String protocolo,
			@Value("${apis.crypto-provider.ripio.dominio}") String dominio,
			@Value("${apis.crypto-provider.ripio.urls.cotizacion-alta}") String apiPath) {
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
	}

	
	public Cotizacion run(CotizacionSolicitud solicitud) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); 
				
		RestTemplate restTemplate = new RestTemplate(); 
		
		JSONObject loginJsonDto = new JSONObject();
		try {
			loginJsonDto.put("activoBase", solicitud.getActivoBase());
			loginJsonDto.put("activoCoti", solicitud.getActivoCoti());
		} catch (JSONException e) {
			log.error("JSONException: " + e.getMessage());
			throw new LoginException("Error al construir el JSON de login", e.toString());
		} 
		     
		HttpEntity<String> request =  new HttpEntity<String>(loginJsonDto.toString(), headers);
		 
		
		ResponseEntity<String> response;
		try {
			log.debug("buildUrl(): " + buildUrl());
			response = restTemplate.postForEntity(buildUrl(), request, String.class);
			log.debug("response: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new LoginException(HttpStatus.NOT_FOUND.toString(), "CrearCotizacionAdapterOut() - Resource not found: " + e.getMessage() );
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());
			throw new LoginException("4xx / 5xx", "CrearCotizacionAdapterOut() - HTTP Error: " + e.getMessage() );
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());
			throw new LoginException("ERROR-INESPERADO", "CrearCotizacionAdapterOut() - Error en restTemplate: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new LoginException(response.getStatusCode().toString(), "Error al obtener el token de login");
		}
		
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			jsonMapper.registerModule(new JavaTimeModule()); 
			jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); 
			
			//TODO: VER esto !!
			//jsonMapper.setAnnotationIntrospector(new LoginEscoDtoDynamicAliasIntrospector());
			
			Cotizacion cotizacion = jsonMapper.readValue(response.getBody(), Cotizacion.class); 	
			//LoginTokenRipio dto = mapper.run(tokenDto);	
			
			return cotizacion;			
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new LoginException("Error al mapear el JSON a TokenDto", e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new LoginException("Error al procesar el JSON", e.toString());
		}
		
	}
	
	private String buildUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append(protocolo);
		sb.append("://");
		sb.append(dominio);
		sb.append(apiPath);

		return sb.toString();
	}	
}
