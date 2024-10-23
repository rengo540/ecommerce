package com.example.ecommerce.services.iservices;


import com.example.ecommerce.models.Category;

import java.util.List;

public interface ICategoryService {

    Category addCategory(Category category);
    void deleteCategory(Long categoryId);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Category updateCategory(Category category, Long id);

}
