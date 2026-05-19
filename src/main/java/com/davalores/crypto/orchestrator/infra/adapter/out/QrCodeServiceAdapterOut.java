package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.QrCodeServicePortOut;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QrCodeServiceAdapterOut implements QrCodeServicePortOut {

	
	@Override
	public String generarBase64(String otpauthUrl, int width, int height) 
	        throws WriterException, IOException {
	    byte[] qrCodeBytes = generar(otpauthUrl, width, height);
	    String base64QrCode = Base64.getEncoder().encodeToString(qrCodeBytes);
	    return "data:image/png;base64," + base64QrCode;
	}
	
	@Override
	public byte[] generar(String valor, int ancho, int  alta) throws WriterException, IOException {

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(valor, BarcodeFormat.QR_CODE, ancho, alta);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();        
		
	}

}
