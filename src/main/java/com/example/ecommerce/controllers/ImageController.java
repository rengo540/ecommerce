package com.example.ecommerce.controllers;

import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.services.iservices.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    @Autowired
    private IImageService imageService;

    
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,
                                                  @RequestParam Long productId) {
        try {
            imageService.addImages(files,productId);
            return ResponseEntity.ok().body(new ApiResponse("successfully added",1));
        }catch (IOException e){
            return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse(e.getMessage(),BAD_REQUEST));
        }

    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        try {
            imageService.deleteImageById(imageId);
            return ResponseEntity.ok(new ApiResponse("deleted successfully", null));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewImage(@PathVariable String filename) {
        try {
            Resource resource = imageService.viewImage(filename);

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(404).body(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}