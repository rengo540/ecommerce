package com.example.ecommerce.services;

import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.models.Image;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repos.ImageRepo;
import com.example.ecommerce.security.SecurityConfig;
import com.example.ecommerce.services.iservices.IImageService;
import com.example.ecommerce.services.iservices.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ImageService implements IImageService {

    @Autowired
    private ImageRepo imageRepo;


    @Autowired
    private IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("this image not found id="+id));
    }

    @Override
    public void deleteImageById(Long id) throws IOException {
         Image image = getImageById(id);
         Path filePath = Paths.get(SecurityConfig.UPLOAD_DIR +image.getId().toString()+image.getFileName());
         Files.delete(filePath);
         imageRepo.deleteById(id);
    }

    @Override
    public void addImages(List<MultipartFile> files, Long productId) throws IOException {
        if (files.isEmpty()) {
            throw new IOException("there is no file");
        }

        Product product = productService.getProductById(productId);

        for(MultipartFile file :files){
            String originalFilename = file.getOriginalFilename();
            Image image= saveImage(originalFilename,file.getContentType(),productId);
            Path filePath = Paths.get(SecurityConfig.UPLOAD_DIR +image.getId().toString()+originalFilename);
            image.setImagePath(filePath.toString());
            imageRepo.save(image);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Override
    public Resource viewImage(String fileName) throws MalformedURLException {
        Image image = imageRepo.findByFileName(fileName);

        Path filePath =Paths.get(image.getImagePath());
        Resource resource =  new UrlResource(filePath.toUri());

        return resource;
    }

    private Image saveImage(String filename,String filetype,Long productId) {
        Image image = new Image();
        image.setFileName(filename);
        image.setFileType(filetype);
        Product product= productService.getProductById(productId);
        image.setProduct(product);
        return  imageRepo.save(image);
    }




}
