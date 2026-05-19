package com.davalores.crypto.orchestrator.app.port.out;

import java.io.IOException;

import com.google.zxing.WriterException;

public interface QrCodeServicePortOut {

	public byte[] generar(String valor, int ancho, int  alta) throws WriterException, IOException;
	public String generarBase64(String otpauthUrl, int width, int height) throws WriterException, IOException;
	
}
