package com.juanlopera.store.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanlopera.store.entities.Product;

public interface IProductRepository extends JpaRepository<Product,Long>{
    
}
