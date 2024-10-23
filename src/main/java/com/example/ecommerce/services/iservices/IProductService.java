package com.example.ecommerce.services.iservices;

import com.example.ecommerce.dtos.AddProductDto;
import com.example.ecommerce.dtos.OneProductDto;
import com.example.ecommerce.dtos.ProductDto;
import com.example.ecommerce.dtos.ProductUpdateRequest;
import com.example.ecommerce.models.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductDto product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand,String name);
    ProductDto convertToDto(Product product) ;
    OneProductDto convertToOneProductDto(Product product);
    List<ProductDto> getConvertedProducts(List<Product> products);
}
