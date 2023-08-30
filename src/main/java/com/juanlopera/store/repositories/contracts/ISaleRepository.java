package com.juanlopera.store.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanlopera.store.entities.Sale;

public interface ISaleRepository extends JpaRepository<Sale,Long> {
    
}
