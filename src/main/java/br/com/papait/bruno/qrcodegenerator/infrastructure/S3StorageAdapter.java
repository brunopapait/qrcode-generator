package br.com.papait.bruno.qrcodegenerator.infrastructure;

import br.com.papait.bruno.qrcodegenerator.ports.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3StorageAdapter implements StoragePort {

  private final S3Client s3Client;
  private final String bucketName;
  private final String region;

  public S3StorageAdapter(
      @Value("${aws.s3.region}") String region,
      @Value("${aws.s3.bucket-name}") String bucketName
  ) {
    this.region = region;
    this.bucketName = bucketName;

    this.s3Client = S3Client.builder()
        .region(Region.of(this.region))
        .build();
  }

  @Override
  public String uploadFile(byte[] fileData, String fileName, String contentType) {
    final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(fileName)
        .contentType(contentType)
        .build();

    s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));

    System.out.println(s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(fileName)).toString());
    return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
  }
}
