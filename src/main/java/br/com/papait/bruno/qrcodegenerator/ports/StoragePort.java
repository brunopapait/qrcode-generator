package br.com.papait.bruno.qrcodegenerator.ports;

public interface StoragePort {
  String uploadFile(byte[] fileData, String fileName, String contentType);
}
