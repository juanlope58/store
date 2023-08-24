package com.juanlopera.store.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juanlopera.store.entities.Product;
import com.juanlopera.store.repositories.contracts.IProductRepository;
import com.juanlopera.store.services.contracts.IProductService;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public ResponseEntity<List<Product>> findAll() {
                
        try {            
            List<Product> products = this.productRepository.findAll();
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<List<Product>>(HttpStatus.INTERNAL_SERVER_ERROR);
    
        }
    }

    @Override
    public ResponseEntity<Product> create(Product product) {
        try {
            return new ResponseEntity<Product>(this.productRepository.save(product), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            System.out.println("El producto no puede ser null");
            return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);

        }catch (Exception e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Product> update(Product product) {
        try {
            Product updatedProduct = this.productRepository.save(product);
            return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Boolean> delete(Long id) {
        try {
            this.productRepository.deleteById(id);
            return new ResponseEntity<Boolean>(true,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    
    
    
}
