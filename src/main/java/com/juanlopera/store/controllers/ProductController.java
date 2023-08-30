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
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juanlopera.store.entities.Product;
import com.juanlopera.store.services.contracts.IProductService;

import jakarta.websocket.server.PathParam;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/list")
    private ResponseEntity<List<Product>> getAllProducts(){
        return this.productService.findAll();
    }

    @PostMapping(value="/create")
    private ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return this.productService.create(product);
    }
    
    @PutMapping(value="update")
    private ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return this.productService.update(product);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Boolean> deleteProduct(@PathParam("id") Long id){
        return this.productService.delete(id);
    }

    
}
