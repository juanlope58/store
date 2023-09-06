package com.juanlopera.store.services.contracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.juanlopera.store.dto.SaleRequestDTO;
import com.juanlopera.store.entities.Sale;

public interface ISaleService {
    public ResponseEntity<List<Sale>> findAll();
    public ResponseEntity<Sale> create(SaleRequestDTO saleRequest);
    public ResponseEntity<Sale> update(SaleRequestDTO saleRequest);
    public ResponseEntity<Boolean> delete(Long id);
    public ResponseEntity<List<Sale>> findByCustomerId(Long customerId);
    public ResponseEntity<List<Sale>> findByDate(LocalDate date);
}
