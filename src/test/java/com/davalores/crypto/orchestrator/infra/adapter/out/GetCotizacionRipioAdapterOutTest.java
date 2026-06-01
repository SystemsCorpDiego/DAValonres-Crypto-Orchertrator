package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.davalores.crypto.orchestrator.domain.model.Cotizacion;

@SpringBootTest(properties = { 
		"apis.crypto-provider.ripio.protocolo-key=http",
		"apis.crypto-provider.ripio.dominio=localhost:8093",
		"apis.crypto-provider.ripio.cotizacion-consul=/cripto-provider/ripio/cotizaciones/"})
public class GetCotizacionRipioAdapterOutTest {

	@Value("${apis.crypto-provider.ripio.protocolo}") String protocolo;
	@Value("${apis.crypto-provider.ripio.dominio}") String dominio;
	@Value("${apis.crypto-provider.ripio.urls.cotizacion-consul}") String apiPath;
	
	
	//El testing necesita levantar cripto-provider
	// @Test
	public void run() {
		String cotizacionId = "e89a6f79-cead-4b46-a130-8c1463b8fc71";
		GetCotizacionRipioAdapterOut adapter = new GetCotizacionRipioAdapterOut(protocolo, dominio, apiPath);		
		
		 
		Cotizacion response = adapter.run(cotizacionId);
		
		System.out.println("response: " + response);
		
	}
}
