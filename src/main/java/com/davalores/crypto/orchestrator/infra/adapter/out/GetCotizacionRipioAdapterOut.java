package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.orchestrator.app.port.out.GetCotizacionRipioPortOut;
import com.davalores.crypto.orchestrator.domain.model.Cotizacion;
import com.davalores.crypto.orchestrator.domain.model.exception.CotizacionException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetCotizacionRipioAdapterOut implements GetCotizacionRipioPortOut {
	
	private final String protocolo; 
	private final String dominio; 
	private final String apiPath; 
	
	public GetCotizacionRipioAdapterOut(
			@Value("${apis.crypto-provider.ripio.protocolo}") String protocolo,
			@Value("${apis.crypto-provider.ripio.dominio}") String dominio,
			@Value("${apis.crypto-provider.ripio.urls.cotizacion-consul}") String apiPath) {
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
	}

	
	@Override
	public Cotizacion run(String cotizacionId) {

		//Seteo de Headers

		//Sin Body
		
		
		//Realizo la llamada a la API de Ripio
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<String> response = null;
		String sUrl = buildUrl(cotizacionId);
		
		try {
			log.debug("buildUrl(): " + sUrl);
			response = restTemplate.getForEntity(sUrl, String.class);			
			//response = restTemplate.exchange(sUrl, HttpMethod.GET, requestEntity, String.class);			
			log.debug("response: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new CotizacionException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Resource not found: " + sUrl );
		} catch (HttpClientErrorException.Unauthorized e) {
			// Handle 401 specifically
			log.error("Unauthorized: " + e.getMessage());
			throw new CotizacionException(ErrorCodeEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "login no autorizado");			
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());			
			if ( e.getStatusCode().is5xxServerError() ) {
				throw new CotizacionException(""+e.getStatusCode().value(), ErrorCodeEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + sUrl  + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
			} else {
				throw new CotizacionException(ErrorCodeEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + sUrl  + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
			}
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());
			throw new CotizacionException(ErrorCodeEnum.UNEXPECTED_ERROR.toString(), "Error Msg: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new CotizacionException(ErrorCodeEnum.HTTP_ERROR.toString(), "HTTP Error: " + response.getStatusCode().toString() + " Error Msg: " + response.getBody());
		}
	
		
		//Casteo a Dominio
		ObjectMapper jsonMapper = new ObjectMapper();
		//Testing Manually register the JSR-310 module
		jsonMapper.registerModule(new JavaTimeModule());
        
		Cotizacion dto = null;
	
		try {
			dto = jsonMapper.readValue(response.getBody(), Cotizacion.class); 											
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new CotizacionException(ErrorCodeEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonMappingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new CotizacionException(ErrorCodeEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonProcessingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		}
		 
		return dto;
	}

	private String buildUrl(String cotizacionId) {
		StringBuilder sb = new StringBuilder();
		sb.append(protocolo);
		sb.append("://");
		sb.append(dominio);
		sb.append(apiPath);
		sb.append("/");
		sb.append(cotizacionId);
		sb.append("/");

		return sb.toString();
	}	

}
