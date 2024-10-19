//package com.ecom.product.business.product.resource;
//
//import com.ecom.product.business.product.service.S3Service;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/images")
//@RequiredArgsConstructor
//public class ImageUploadController {
//
//    private final S3Service s3Service;
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            // Upload the file to S3 and get the file URL
//            String fileUrl = s3Service.uploadFile(file);
//            return new ResponseEntity<>("File uploaded successfully: " + fileUrl, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Error occurred during file upload", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
