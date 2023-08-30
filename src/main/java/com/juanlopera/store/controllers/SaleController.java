package com.juanlopera.store.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juanlopera.store.entities.Sale;
import com.juanlopera.store.services.contracts.ISaleService;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    @GetMapping("/list")
    private ResponseEntity<List<Sale>> getAllSales(){
        return this.saleService.findAll();
    }

    @PostMapping(value="/create")
    private ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        return this.saleService.create(sale);
    }
    
    @PutMapping(value="update")
    private ResponseEntity<Sale> updateSale(@RequestBody Sale sale) {
        return this.saleService.update(sale);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Boolean> deleteSale(@PathParam("id") Long id){
        return this.saleService.delete(id);
    }
    
    
}
