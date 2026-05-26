package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.junit.jupiter.api.Test;

public class CypherServiceAdapterOutTest {

	CypherServiceAdapterOut cypher = new CypherServiceAdapterOut("Scre-to.159-AuZ-tero.753xFRD345dfr#456x");
	
	@Test
	public void run() {
		String texto = " X - para todos - Z.-";
		System.out.print( "texto: " + texto);
		
		String cyphed = cypher.encriptar(texto);
		System.out.print( "cyphed: " + cyphed);
				
		String texto2 = cypher.desencriptar(cyphed);
		System.out.print( "texto2: " + texto2);
		
	}
	
}
