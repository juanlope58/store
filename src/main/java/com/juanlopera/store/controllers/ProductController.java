package com.juanlopera.store.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juanlopera.store.dto.ProductRequestDTO;
import com.juanlopera.store.entities.Product;
import com.juanlopera.store.services.contracts.IProductService;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/list")
    private ResponseEntity<List<Product>> getAllProducts(){
        return this.productService.findAll();
    }

    @PostMapping("/create")
    private ResponseEntity<Product> createProduct(@RequestBody ProductRequestDTO productRequest) {
        return this.productService.create(productRequest);
    }
    
    @PutMapping("/update")
    private ResponseEntity<Product> updateProduct(@RequestBody ProductRequestDTO productRequest) {
        return this.productService.update(productRequest);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Boolean> deleteProduct(@PathVariable("id") Long id){
        return this.productService.delete(id);
    }

    
}
