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

import com.davalores.crypto.orchestrator.app.port.out.CypherServicePortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetDetalleCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.LoginEscoBolsaPortOut;
import com.davalores.crypto.orchestrator.domain.model.DetalleCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.UsuarioEsco;
import com.davalores.crypto.orchestrator.domain.model.exception.DetalleCuentaEscoException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.infra.adapter.out.dto.DetalleCuentaEscoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetDetalleCuentaEscoAdapterOut implements GetDetalleCuentaEscoPortOut {

	private final LoginEscoBolsaPortOut loginEscoBolsa;
	private final CypherServicePortOut cypherServicePortOut;
	
	private String protocolo; 
	private String dominio; 
	private String apiPath; 
	private String apiVersion; 

	public GetDetalleCuentaEscoAdapterOut(
			@Value("${apis.esco-bolsa.protocolo}") String protocolo,
			@Value("${apis.esco-bolsa.dominio}") String dominio, 
			@Value("${apis.esco-bolsa.urls.detalleCuenta}") String apiPath,
			@Value("${apis.esco-bolsa.api-version}") String apiVersion,
			LoginEscoBolsaPortOut loginEscoBolsa,
			CypherServicePortOut cypherServicePortOut) {
		super();
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
		this.apiVersion = apiVersion;
		this.loginEscoBolsa = loginEscoBolsa;
		this.cypherServicePortOut = cypherServicePortOut;
	}

	
	@Override
	public DetalleCuentaEsco run(String token) {
		log.debug("inputParam -> token: {} ", token);
		DetalleCuentaEsco detalleCuentaEsco = null;
		

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); 
		headers.add("api-version", apiVersion);
		headers.setBearerAuth(token);
		
		RestTemplate restTemplate = new RestTemplate(); 
		
		JSONObject requestPaginationJsonDto = new JSONObject();
		JSONObject requestJsonDto = new JSONObject();
		try {
			requestPaginationJsonDto.put("pageNumber", "0");
			requestPaginationJsonDto.put("pageSize", "0");
			
			requestJsonDto.put("cuenta", "0");
			requestJsonDto.put("timeStamp", "0");
			requestJsonDto.put("paramPagination", requestPaginationJsonDto);
		} catch (JSONException e) {
			log.error("LoginRipioAdapterOut() - JSONException: " + e.getMessage());
			throw new DetalleCuentaEscoException(ErrorCodeEnum.JSON_MAPPER_SERIALIZE_ERROR.toString(),  "Error en parametros de login" + e.toString());
		} 
		     
		HttpEntity<String> request =  new HttpEntity<String>(requestJsonDto.toString(), headers);
		 
		
		String apiUrl = buildUrl();
		ResponseEntity<String> response = null;
		try {
			log.debug("buildUrl(): " + apiUrl);
			response = restTemplate.postForEntity(apiUrl, request, String.class);
			log.debug("response: " + response.toString());
		} catch (HttpClientErrorException.NotFound e) {
		    // Handle 404 specifically
		    log.error("Resource not found: " + e.getMessage());		    
		    throw new DetalleCuentaEscoException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Resource not found: " + apiUrl );
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());
			throw new DetalleCuentaEscoException(ErrorCodeEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + apiUrl + " RequestBody: " + request + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());
			throw new DetalleCuentaEscoException(ErrorCodeEnum.UNEXPECTED_ERROR.toString(), "Error Msg: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new DetalleCuentaEscoException(ErrorCodeEnum.HTTP_ERROR.toString(), "HTTP Error: " + response.getStatusCode().toString() + " Error Msg: " + response.getBody());
		}
		
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			DetalleCuentaEscoDto detalleCuentaEscoDto = jsonMapper.readValue(response.getBody(), DetalleCuentaEscoDto.class); 	
			if ( detalleCuentaEscoDto != null &&
					detalleCuentaEscoDto.getData() != null &&
					detalleCuentaEscoDto.getData().size()>0	&&
					detalleCuentaEscoDto.getData().getFirst().getCuentaDetalle()!= null					
					) {
				detalleCuentaEsco = new DetalleCuentaEsco();
				detalleCuentaEsco.setComitente(  detalleCuentaEscoDto.getData().getFirst().getCuentaDetalle().getNumComitente() );
				detalleCuentaEsco.setComitenteDescripcion( detalleCuentaEscoDto.getData().getFirst().getCuentaDetalle().getDescComitente() );
				detalleCuentaEsco.setCuit( detalleCuentaEscoDto.getData().getFirst().getCuentaDetalle().getCuit() );
			}
			
			if ( detalleCuentaEsco == null)
				throw new DetalleCuentaEscoException(ErrorCodeEnum.HTTP_NOT_FOUND.toString(), "No se pudo recuperar la cuenta ESCO");
			
			
			return detalleCuentaEsco;			
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new DetalleCuentaEscoException(ErrorCodeEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonMappingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new DetalleCuentaEscoException(ErrorCodeEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonProcessingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		}
	}

	@Override
	public DetalleCuentaEsco run(String usuario, String clave) {		
		UsuarioEsco usuarioEsco = loginEscoBolsa.run(usuario, clave);		
		return run(usuarioEsco.getAccessToken());
	}

	@Override
	public DetalleCuentaEsco run(Usuario usuario) {
		String clave = cypherServicePortOut.desencriptar(usuario.getClaveEsco());
		return run(usuario.getUsuario(), clave);
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
