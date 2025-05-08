package br.com.papait.bruno.qrcodegenerator.controller;

import br.com.papait.bruno.qrcodegenerator.dto.qrcode.QrCodeGenerateRequest;
import br.com.papait.bruno.qrcodegenerator.dto.qrcode.QrCodeGenerateResponse;
import br.com.papait.bruno.qrcodegenerator.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

  private final QrCodeGeneratorService qrCodeGeneratorService;

  public QrCodeController(QrCodeGeneratorService qrCodeGeneratorService) {
    this.qrCodeGeneratorService = qrCodeGeneratorService;
  }

  @PostMapping("/generate")
  public ResponseEntity<QrCodeGenerateResponse> genereateQrCode(@RequestBody QrCodeGenerateRequest qrCodeGenerateRequest) {
    try {
      return ResponseEntity.ok(qrCodeGeneratorService.generateAndUploadQrCode(qrCodeGenerateRequest.text()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
