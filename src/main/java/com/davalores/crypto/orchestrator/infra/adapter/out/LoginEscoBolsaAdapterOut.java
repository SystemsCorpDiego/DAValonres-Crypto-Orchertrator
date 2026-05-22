package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.davalores.crypto.orchestrator.app.port.out.LoginEscoBolsaPortOut;
import com.davalores.crypto.orchestrator.domain.model.UsuarioEsco;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCoreEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.davalores.crypto.orchestrator.infra.adapter.out.mapper.LoginEscoDtoDynamicAliasIntrospector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginEscoBolsaAdapterOut implements LoginEscoBolsaPortOut {

	private String protocolo; 
	private String dominio; 
	private String apiPath; 
	private String apiVersion; 

	public LoginEscoBolsaAdapterOut(@Value("${apis.esco-bolsa.protocolo}") String protocolo,
			@Value("${apis.esco-bolsa.dominio}") String dominio, 
			@Value("${apis.esco-bolsa.urls.login}") String apiPath,
			@Value("${apis.esco-bolsa.api-version}") String apiVersion) {
		super();
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
		this.apiVersion = apiVersion;
	}
	
	
	@Override
	public UsuarioEsco run(String usuario, String clave) {
		log.debug("inputParam -> usuario: {} clave: {}", usuario, clave);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); 
		headers.add("api-version", apiVersion);
		
		
		RestTemplate restTemplate = new RestTemplate(); 
		
		JSONObject loginJsonDto = new JSONObject();
		try {
			loginJsonDto.put("userName", usuario);
			loginJsonDto.put("password", clave);
		} catch (JSONException e) {
			log.error("LoginRipioAdapterOut() - JSONException: " + e.getMessage());
			throw new LoginException(ErrorCoreEnum.JSON_MAPPER_SERIALIZE_ERROR.toString(),  "Error en parametros de login" + e.toString());
		} 
		     
		HttpEntity<String> request =  new HttpEntity<String>(loginJsonDto.toString(), headers);
		 
		
		String apiUrl = buildUrl();
		ResponseEntity<String> response = null;
		try {
			log.debug("buildUrl(): " + apiUrl);
			response = restTemplate.postForEntity(apiUrl, request, String.class);
			log.debug("response: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    //throw new LoginException(HttpStatus.NOT_FOUND.toString(), "CotizarRipioAdapterOut() - Resource not found: " + e.getMessage() );
		    throw new LoginException(ErrorCoreEnum.HTTP_NOT_FOUND.toString(), "Resource not found: " + apiUrl );
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());
			throw new LoginException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + apiUrl + " RequestBody: " + request + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());
			throw new LoginException(ErrorCoreEnum.UNEXPECTED_ERROR.toString(), "Error Msg: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new LoginException(ErrorCoreEnum.HTTP_ERROR.toString(), "HTTP Error: " + response.getStatusCode().toString() + " Error Msg: " + response.getBody());
		}
		
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			jsonMapper.setAnnotationIntrospector(new LoginEscoDtoDynamicAliasIntrospector());
			
			UsuarioEsco usuarioEsco = jsonMapper.readValue(response.getBody(), UsuarioEsco.class); 	
			//LoginTokenRipio dto = mapper.run(tokenDto);	
			
			return usuarioEsco;			
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new LoginException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonMappingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new LoginException(ErrorCoreEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonProcessingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
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
