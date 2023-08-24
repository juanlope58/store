package com.juanlopera.store.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juanlopera.store.entities.Category;
import com.juanlopera.store.services.contracts.ICategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/list")
    private ResponseEntity<List<Category>> getAllCategories(){
        return this.categoryService.findAll();
    }

    @PostMapping("/create")
    private ResponseEntity<Category> createCategory(@RequestBody Category category){
        return this.categoryService.create(category);
    }

    @PutMapping("/update")
    private ResponseEntity<Category> updateCategory(@RequestBody Category category){
        return this.categoryService.udate(category);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<Boolean> deleteCategory(@RequestParam Long id){
        return this.categoryService.delete(id);
    }    
}
