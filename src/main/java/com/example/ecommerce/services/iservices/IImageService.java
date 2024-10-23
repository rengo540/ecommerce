package com.example.ecommerce.services.iservices;

import com.example.ecommerce.models.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface IImageService {

    Image getImageById(Long id);
    void deleteImageById(Long id) throws IOException;
    void addImages(List<MultipartFile> files, Long productId) throws IOException;
    Resource viewImage(String fileName) throws MalformedURLException;
}
