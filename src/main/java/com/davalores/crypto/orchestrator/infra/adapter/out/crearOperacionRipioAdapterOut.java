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

import com.davalores.crypto.orchestrator.app.port.out.CrearOperacionRipioPortOut;
import com.davalores.crypto.orchestrator.domain.model.CrearOperacion;
import com.davalores.crypto.orchestrator.domain.model.Operacion;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class crearOperacionRipioAdapterOut implements CrearOperacionRipioPortOut {
	
	private final String protocolo; 
	private final String dominio; 
	private final String apiPath; 
	
	public crearOperacionRipioAdapterOut(
			@Value("${apis.crypto-provider.ripio.protocolo}") String protocolo,
			@Value("${apis.crypto-provider.ripio.dominio}") String dominio,
			@Value("${apis.crypto-provider.ripio.urls.operacion-alta}") String apiPath) {
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
	}
	
	@Override
	public Operacion run(CrearOperacion dto) {
        log.debug("run -> dto: {}", dto);		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); 
				
		RestTemplate restTemplate = new RestTemplate(); 
		
		JSONObject loginJsonDto = new JSONObject();
		try {
			loginJsonDto.put("idExternoCliente", dto.getRipioId());
			loginJsonDto.put("idExternoProveedorCotizacion", dto.getCotizacionId());
			loginJsonDto.put("cantidad", dto.getCantidad());
		} catch (JSONException e) {
			log.error("JSONException: " + e.getMessage());
			throw new LoginException("Error al construir el JSON de login", e.toString());
		} 
		     
		HttpEntity<String> request =  new HttpEntity<String>(loginJsonDto.toString(), headers);
		 
		
		ResponseEntity<String> response;
		try {
			log.debug("buildUrl(): " + buildUrl(dto));
			response = restTemplate.postForEntity(buildUrl(dto), request, String.class);
			log.debug("response: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new LoginException(HttpStatus.NOT_FOUND.toString(), "crearOperacionRipioAdapterOut() - Resource not found: " + e.getMessage() );
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());
			throw new LoginException("4xx / 5xx", "crearOperacionRipioAdapterOut() - HTTP Error: " + e.getMessage() );
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());
			throw new LoginException("ERROR-INESPERADO", "crearOperacionRipioAdapterOut() - Error en restTemplate: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new LoginException(response.getStatusCode().toString(), "Error al obtener el token de login");
		}
		
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			//jsonMapper.registerModule(new JavaTimeModule()); 
			//jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); 
			jsonMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
			jsonMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
			
			Operacion operacion = jsonMapper.readValue(response.getBody(), Operacion.class); 
			operacion.setUsuarioId(dto.getUsuarioId());
			operacion.setCotizacionId(dto.getCotizacionId());
			
			//LoginTokenRipio dto = mapper.run(tokenDto);	
			
			return operacion;			
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new LoginException("Error al mapear el JSON a TokenDto", e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new LoginException("Error al procesar el JSON", e.toString());
		}
	}
	
	private String buildUrl(CrearOperacion dto) {
		StringBuilder sb = new StringBuilder();
		sb.append(protocolo);
		sb.append("://");
		sb.append(dominio);
		sb.append(apiPath);
		sb.append("/");
		sb.append(dto.getTipo());
		sb.append("/");

		return sb.toString();
	}	
}
