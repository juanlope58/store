package com.juanlopera.store.services.contracts;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.juanlopera.store.entities.Sale;

public interface ISaleService {
    public ResponseEntity<List<Sale>> findAll();
    public ResponseEntity<Sale> create(Sale sale);
    public ResponseEntity<Sale> update(Sale sale);
    public ResponseEntity<Boolean> delete(Long id);
}
