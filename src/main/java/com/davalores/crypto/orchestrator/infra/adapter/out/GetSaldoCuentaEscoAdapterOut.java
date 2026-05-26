package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

import com.davalores.crypto.orchestrator.app.port.out.GetSaldoCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.SaldoCuentaEscoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetSaldoCuentaEscoAdapterOut implements GetSaldoCuentaEscoPortOut {

	private String protocolo; 
	private String dominio; 
	private String apiPath; 
	private String apiVersion; 

	public GetSaldoCuentaEscoAdapterOut(
			@Value("${apis.esco-bolsa.protocolo}") String protocolo,
			@Value("${apis.esco-bolsa.dominio}") String dominio, 
			@Value("${apis.esco-bolsa.urls.tenencias}") String apiPath,
			@Value("${apis.esco-bolsa.api-version}") String apiVersion) {
		super();
		this.protocolo = protocolo;
		this.dominio = dominio;
		this.apiPath = apiPath;
		this.apiVersion = apiVersion;
	}

	
	@Override
	public SaldoCuentaEsco run(String token, String comitente) {
		log.debug("inputParam -> token: {} ", token);
		SaldoCuentaEsco saldoCuentaEsco = null;
		

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); 
		headers.add("api-version", apiVersion);
		headers.setBearerAuth(token);
		
		RestTemplate restTemplate = new RestTemplate(); 
		
		JSONObject requestJsonDto = new JSONObject();
		try {
			requestJsonDto.put("cuentas", comitente);
			requestJsonDto.put("incluirCauciones", "true");
			requestJsonDto.put("monedaValuacion", "ARS");
			requestJsonDto.put("pppMonedaFiltro", "ARS");
			
			ZonedDateTime zdt = ZonedDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			System.out.println( "test-localZdt.format: -> " +zdt.format(formatter) );
			requestJsonDto.put("fecha", zdt.format(formatter) ); 

			requestJsonDto.put("porConcertacion", "true");
			requestJsonDto.put("esConsolidado", "false");
			requestJsonDto.put("agruparPorMoneda", "true");
			requestJsonDto.put("incluirPPP", "true");
			requestJsonDto.put("incluirOpciones", "true");
			requestJsonDto.put("incluirFuturos", "true");
			requestJsonDto.put("valuarPosicion", "true");
			requestJsonDto.put("utilizaCotizacionesOnLine", "true");
			requestJsonDto.put("incluirTitulos", "false"); //TODO: ver
			requestJsonDto.put("incluirFondos", "false"); //TODO: ver
			/*
			 * En desa y para usuario Martin:
			"incluirTitulos": false,
			    SACO:
			        "GGAL"-"534 / GRUPO FINANCIERO GALICIA"
			        "VIST"-"8527 / CEDEAR VISTA OIL"
			        "YPFD"-"710 / YPF S.A. ESCRIT. \"D\" 1 VOTO"
			"incluirFondos": false,
			    SACO:
			        "14176"-"ALLARIA AHORRO Clase A / 14176"
			*/
		} catch (JSONException e) {
			log.error("LoginRipioAdapterOut() - JSONException: " + e.getMessage());
			throw new SaldoCuentaEscoException(ErrorCodeEnum.JSON_MAPPER_SERIALIZE_ERROR.toString(),  "Error en parametros de login" + e.toString());
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
		    throw new SaldoCuentaEscoException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Resource not found: " + apiUrl );
		} catch (HttpStatusCodeException e) {
		    // Handle other HTTP errors (4xx or 5xx)
			log.error("HTTP Error: " + e.getStatusCode());
			throw new SaldoCuentaEscoException(ErrorCodeEnum.HTTP_ERROR.toString(), "HTTP Error: " + e.getStatusCode() +" Url: " + apiUrl + " RequestBody: " + request + " ResponseBody: " + response + " Error Msg: " + e.getMessage() );
		} catch (Exception e) {
			log.error("ERROR-INESPERADO: " + e.toString());
			throw new SaldoCuentaEscoException(ErrorCodeEnum.UNEXPECTED_ERROR.toString(), "Error Msg: " + e.toString());
		}
		
		if ( !response.getStatusCode().is2xxSuccessful() ) {
		    throw new SaldoCuentaEscoException(ErrorCodeEnum.HTTP_ERROR.toString(), "HTTP Error: " + response.getStatusCode().toString() + " Error Msg: " + response.getBody());
		}
		
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			SaldoCuentaEscoDto saldo = null;
			SaldoCuentaEscoDto[] lstSaldoCuentaEscoDto = jsonMapper.readValue(response.getBody(), SaldoCuentaEscoDto[].class); 	
			 
			if ( lstSaldoCuentaEscoDto != null && lstSaldoCuentaEscoDto.length>0 ) {
				for (SaldoCuentaEscoDto reg : lstSaldoCuentaEscoDto) {
				    log.debug("SaldoCuentaEscoDto: {}", reg);
				    if ( reg.getAbreviatura().equals("$") &&
				    		reg.getMoneda().equals("Pesos") &&
				    		reg.getDescripcion().equals("Pesos") ) {
				    	saldo = reg;
				    }
				}
			}
			
			if ( saldo == null )
				throw new SaldoCuentaEscoException(ErrorCodeEnum.NO_DATA_FOUND_ERROR.toString(), "No se pudo recuperar el Saldo de Cuenta ESCO");
				
			
			saldoCuentaEsco = new SaldoCuentaEsco();
			saldoCuentaEsco.setCantidad( saldo.getCantidad() );
			saldoCuentaEsco.setComitente(comitente);		
			if ( saldo.getPosicionCauciones() != null &&
					saldo.getPosicionCauciones().getMonto() != null ) {
				if (saldo.getPosicionCauciones().getMonto().compareTo(BigDecimal.ZERO) > 0) {
					saldoCuentaEsco.setCantidad( saldoCuentaEsco.getCantidad().subtract(saldo.getPosicionCauciones().getMonto()) );
				}
			}
			
			return saldoCuentaEsco;			
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: " + e.getMessage());
			throw new SaldoCuentaEscoException(ErrorCodeEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonMappingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException: " + e.getMessage());
			throw new SaldoCuentaEscoException(ErrorCodeEnum.JSON_MAPPER_DESERIALIZE_ERROR.toString(), "Error JsonProcessingException - Response.body: " + response.getBody() + " - Error: " + e.toString());
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
