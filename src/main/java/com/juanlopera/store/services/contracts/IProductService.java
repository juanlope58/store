package com.juanlopera.store.services.contracts;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.juanlopera.store.dto.ProductRequestDTO;
import com.juanlopera.store.entities.Product;

public interface IProductService {
    public ResponseEntity<List<Product>> findAll();
    public ResponseEntity<Product> create(ProductRequestDTO productRequest);
    public ResponseEntity<Product> update(ProductRequestDTO productRequest);
    public ResponseEntity<Boolean> delete(Long id);
}
