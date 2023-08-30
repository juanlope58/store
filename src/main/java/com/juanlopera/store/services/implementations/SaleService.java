package com.juanlopera.store.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.juanlopera.store.entities.Sale;
import com.juanlopera.store.repositories.contracts.ISaleRepository;
import com.juanlopera.store.services.contracts.ISaleService;

public class SaleService implements ISaleService {

    @Autowired
    private ISaleRepository saleRepository;

    @Override
    public ResponseEntity<List<Sale>> findAll() {
        try {
            return new ResponseEntity<List<Sale>>(this.saleRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Sale>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Sale> create(Sale sale) {
        try {
            return new ResponseEntity<Sale>(this.saleRepository.save(sale), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Sale>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Sale> update(Sale sale) {
        try {
            return new ResponseEntity<Sale>(this.saleRepository.save(sale) ,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Sale>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Boolean> delete(Long id) {
        try {
            this.saleRepository.deleteById(id);
            return new ResponseEntity<Boolean>(true,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
