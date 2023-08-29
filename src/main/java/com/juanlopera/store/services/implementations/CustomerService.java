package com.juanlopera.store.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juanlopera.store.entities.Customer;
import com.juanlopera.store.repositories.contracts.ICustomerRepository;
import com.juanlopera.store.services.contracts.ICustomerService;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public ResponseEntity<List<Customer>> listAll() {
        try {
            List<Customer> customers = new ArrayList<>();
            customers = this.customerRepository.findAll();
            return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<List<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Customer> create(Customer customer) {
        try {
            return new ResponseEntity<Customer>(this.customerRepository.save(customer), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Customer> update(Customer customer) {
        try {
            return new ResponseEntity<Customer>(this.customerRepository.save(customer),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Boolean> delete(Long id) {
        try {
            this.customerRepository.deleteById(id);
            return new ResponseEntity<Boolean>(true,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
