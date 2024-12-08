package com.example.ecommerce.controllers;

import com.example.ecommerce.exceptions.AlreadyExistsException;
import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.services.iservices.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ApiResponse> addCategory(@RequestParam String categoryName){
            Category category = new Category();
            category.setName(categoryName);
            Category addedCategory =categoryService.addCategory(category);
            return ResponseEntity.ok().body(new ApiResponse("successfully added",addedCategory));

    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategories(){
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("found",categories));

    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("found",category));

    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable String categoryName){
            Category category = categoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new ApiResponse("found",category));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
            categoryService.deleteCategory(id);
            return  ResponseEntity.ok(new ApiResponse("deleted successfully", null));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {

            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Update success!", updatedCategory));
    }
}
