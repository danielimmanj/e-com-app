package com.ecom.product.business.product.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

//@Service
public class S3Service {

    private static final String BUCKET_NAME = "ecommerceproductimages";
    private static final String REGION = "ap-southeast-2"; // Corrected region
    private static final String ACCESS_KEY = "AKIA23WHUJOJ6SOV7GAA"; // Ensure to secure your credentials
    private static final String SECRET_KEY = "f7bbkIe0Ztx95obOtkyjqk9gzeAMz7H80enuy2at"; // Ensure to secure your credentials

    private S3Client s3Client;

//    @PostConstruct
    private void initializeS3Client() {
        try {
            s3Client = S3Client.builder()
                    .region(Region.of(REGION))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                    .build();
        } catch (Exception e) {
            System.err.println("Failed to initialize S3 client: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalStateException("Failed to initialize S3 client: " + e.getMessage(), e);
        }
    }

    public String uploadFile(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileName)
                    .acl("public-read")  // Optional: Adjust ACL as needed
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(file.getBytes()));

            return "https://" + BUCKET_NAME + ".s3." + REGION + ".amazonaws.com/" + fileName;
        } catch (S3Exception | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error uploading file to S3: " + e.getMessage());
        }
    }
}