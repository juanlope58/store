package com.juanlopera.store.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanlopera.store.entities.Category;

public interface ICategoryRepository extends JpaRepository<Category,Long>{
    
}
