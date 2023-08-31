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

import com.juanlopera.store.entities.Customer;
import com.juanlopera.store.services.contracts.ICustomerService;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return this.customerService.listAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        return this.customerService.create(customer);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
        return this.customerService.update(customer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable(value = "id") Long id){
        return this.customerService.delete(id);
    }





}
