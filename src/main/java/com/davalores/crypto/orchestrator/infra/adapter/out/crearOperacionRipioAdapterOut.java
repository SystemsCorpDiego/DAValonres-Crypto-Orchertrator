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
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCoreEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.davalores.crypto.orchestrator.domain.model.exception.OperacionException;
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
        log.debug("inputParam -> {}", dto);		
		
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
			throw new OperacionException(ErrorCoreEnum.JSON_MAPPER_SERIALIZE_ERROR.toString(),  "Error en parametros de entrada " + e.toString());
		} 
		     
		HttpEntity<String> request =  new HttpEntity<String>(loginJsonDto.toString(), headers);
		 
		String apiUrl = buildUrl(dto);
		ResponseEntity<String> response = null;
		try {
			log.debug("buildUrl(): " + apiUrl);
			response = restTemplate.postForEntity(apiUrl, request, String.class);
			log.debug("response: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new OperacionException(ErrorCoreEnum.HTTP_NOT_FOUND.toString(), "Resource not found: " + apiUrl );
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());
			//throw new LoginException("4xx / 5xx", "crearOperacionRipioAdapterOut() - HTTP Error: " + e.getMessage() );
			throw new OperacionException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + apiUrl + " RequestBody: " + request + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());			
			throw new OperacionException(ErrorCoreEnum.UNEXPECTED_ERROR.toString(), "Error Msg: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {		    
		    throw new OperacionException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + response.getStatusCode().toString() + " Error Msg: " + response.getBody());
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
			
			log.debug("outputParam -> {}", operacion);
			return operacion;			
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			//throw new LoginException("Error al mapear el JSON a TokenDto", e.toString());
			throw new OperacionException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonMappingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new OperacionException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonProcessingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
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
