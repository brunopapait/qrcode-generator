package br.com.papait.bruno.qrcodegenerator.service;

import br.com.papait.bruno.qrcodegenerator.dto.qrcode.QrCodeGenerateResponse;
import br.com.papait.bruno.qrcodegenerator.ports.StoragePort;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import static com.google.zxing.BarcodeFormat.QR_CODE;

@Service
public class QrCodeGeneratorService {

  private final StoragePort storagePort;

  public QrCodeGeneratorService(StoragePort storagePort) {
    this.storagePort = storagePort;
  }

  public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
    final QRCodeWriter qrCodeData = new QRCodeWriter();

    final BitMatrix bitMatrix = qrCodeData.encode(text, QR_CODE, 200, 200);
    final ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

    final byte[] pngData = pngOutputStream.toByteArray();

    final String url = storagePort.uploadFile(pngData, UUID.randomUUID().toString(), "image/png");
    return new QrCodeGenerateResponse(url);
  }
}
