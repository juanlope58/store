package com.juanlopera.store.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    // {
    //     "customerId":2,
    //     "productIds":[7,1,8]
    // }
    @PostMapping("/create")
    private ResponseEntity<Sale> createSale(@RequestBody SaleRequestDTO saleRequest) {
        return this.saleService.create(saleRequest);
    }
    
    // {
    //     "id":16,
    //     "customerId":2,
    //     "productIds": [1, 4, 8]
    // }
    @PutMapping("update")
    private ResponseEntity<Sale> updateSale(@RequestBody SaleRequestDTO saleRequest) {
        return this.saleService.update(saleRequest);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Boolean> deleteSale(@PathVariable("id") Long id){
        return this.saleService.delete(id);
    }
    
    @GetMapping("/salesByCustomer/{customerId}")
    private ResponseEntity<List<Sale>> getSalesByCustomer(@PathVariable Long customerId){
        return this.saleService.findByCustomerId(customerId);
    }

    // {
    //     "date":"2023-09-05"
    // }
    @GetMapping("/salesByDate")
    private ResponseEntity<List<Sale>> getSalesByDate(@RequestBody SaleRequestDTO saleRequest){
        try {
            LocalDate date = LocalDate.parse(saleRequest.getDate());
            return this.saleService.findByDate(date);
        }catch(DateTimeParseException e){
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    // {
    //     "customerId":1,
    //     "startDate":"2023-08-20",
    //     "endDate":"2023-09-06"
    
    // }
    @GetMapping("/salesByCustomerBetweenDates")
    private ResponseEntity<List<Sale>> getSalesBetweenDates(@RequestBody SaleRequestDTO saleRequest){
        try {
            LocalDate date1 = LocalDate.parse(saleRequest.getStartDate());
            LocalDate date2 = LocalDate.parse(saleRequest.getEndDate());
            Long customerId = saleRequest.getCustomerId();
            return this.saleService.findByCustomerAndDateBetween(customerId, date1, date2);

        }catch(DateTimeParseException e){
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
