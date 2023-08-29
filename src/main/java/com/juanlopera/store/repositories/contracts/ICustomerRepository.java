package com.juanlopera.store.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanlopera.store.entities.Customer;

public interface ICustomerRepository extends JpaRepository<Customer,Long>{
    
}
