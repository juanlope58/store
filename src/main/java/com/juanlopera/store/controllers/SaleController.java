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

import com.juanlopera.store.dto.SaleRequestDTO;
import com.juanlopera.store.entities.Sale;
import com.juanlopera.store.services.contracts.ISaleService;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    @GetMapping("/list")
    private ResponseEntity<List<Sale>> getAllSales(){
        return this.saleService.findAll();
    }

    @PostMapping("/create")
    private ResponseEntity<Sale> createSale(@RequestBody SaleRequestDTO saleRequest) {
        return this.saleService.create(saleRequest);
    }
    
    @PutMapping("update")
    private ResponseEntity<Sale> updateSale(@RequestBody SaleRequestDTO saleRequest) {
        return this.saleService.update(saleRequest);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Boolean> deleteSale(@PathVariable("id") Long id){
        return this.saleService.delete(id);
    }
    
    
}
