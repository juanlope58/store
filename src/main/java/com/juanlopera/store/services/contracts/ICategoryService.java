package com.juanlopera.store.services.contracts;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.juanlopera.store.entities.Category;

public interface ICategoryService {
    public ResponseEntity<List<Category>> findAll();
    public ResponseEntity<Category> create(Category category);
    public ResponseEntity<Category> udate(Category category);
    public ResponseEntity<Boolean> delete(Long id);
}
