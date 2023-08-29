package com.juanlopera.store.services.contracts;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.juanlopera.store.entities.Customer;

public interface ICustomerService {
    public ResponseEntity<List<Customer>> listAll();
    public ResponseEntity<Customer> create(Customer customer);
    public ResponseEntity<Customer> update(Customer customer);
    public ResponseEntity<Boolean> delete(Long id);
}
