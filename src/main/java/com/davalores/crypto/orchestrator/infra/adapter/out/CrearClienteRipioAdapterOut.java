package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.orchestrator.app.port.out.CrearClienteRipioPortOut;
import com.davalores.crypto.orchestrator.domain.model.ClienteRipio;
import com.davalores.crypto.orchestrator.domain.model.exception.ClienteRipioException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.davalores.crypto.orchestrator.domain.model.exception.OperacionException;
import com.davalores.crypto.orchestrator.infra.adapter.out.mapper.ClienteRipioDtoDynamicAliasIntrospector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrearClienteRipioAdapterOut implements CrearClienteRipioPortOut {

	private final String protocolo; 
	private final String dominio; 
	private final String apiPath; 
	
	public CrearClienteRipioAdapterOut(
			@Value("${apis.crypto-provider.ripio.protocolo}") String protocolo,
			@Value("${apis.crypto-provider.ripio.dominio}") String dominio,
			@Value("${apis.crypto-provider.ripio.urls.cliente-alta}") String apiPath) {
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
	}
	
	@Override
	public ClienteRipio run() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		RestTemplate restTemplate = new RestTemplate(); 
		
		HttpEntity<String> request =  new HttpEntity<String>(null, headers);
		 
		String apiUrl = buildUrl();
		ResponseEntity<String> response = null;
		try {
			log.debug("buildUrl(): " + apiUrl);
			response = restTemplate.postForEntity(apiUrl, request, String.class);
			log.debug("response: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new ClienteRipioException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Resource not found: " + apiUrl );
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());
			throw new ClienteRipioException(ErrorCodeEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + apiUrl + " RequestBody: " + request + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());
			throw new ClienteRipioException(ErrorCodeEnum.UNEXPECTED_ERROR.toString(), "Error Msg: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {		    
		    throw new ClienteRipioException(ErrorCodeEnum.HTTP_ERROR.toString(), "HTTP Error: " + response.getStatusCode().toString() + " Error Msg: " + response.getBody());
		}
		
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			jsonMapper.setAnnotationIntrospector(new ClienteRipioDtoDynamicAliasIntrospector());
			
			ClienteRipio clienteRipio = jsonMapper.readValue(response.getBody(), ClienteRipio.class); 	
			//LoginTokenRipio dto = mapper.run(tokenDto);	
			
			return clienteRipio;			
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new ClienteRipioException(ErrorCodeEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonMappingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new ClienteRipioException(ErrorCodeEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonProcessingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
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
