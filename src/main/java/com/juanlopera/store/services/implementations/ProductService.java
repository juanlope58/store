package com.juanlopera.store.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juanlopera.store.dto.ProductRequestDTO;
import com.juanlopera.store.entities.Category;
import com.juanlopera.store.entities.Product;
import com.juanlopera.store.repositories.contracts.ICategoryRepository;
import com.juanlopera.store.repositories.contracts.IProductRepository;
import com.juanlopera.store.services.contracts.IProductService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired 
    ICategoryRepository categoryRepository;

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
    public ResponseEntity<Product> create(ProductRequestDTO productRequest) {
        try {
            Product product = new Product();

            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());

            product.setCategory(categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()-> new EntityNotFoundException("No se existe la categoria")));
            
            return new ResponseEntity<Product>(this.productRepository.save(product), HttpStatus.OK);

        }catch(EntityNotFoundException e){
            System.out.println("No existe la categoría en la base de datos");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);

        }catch (Exception e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Product> update(ProductRequestDTO productRequest) {
        try {

            Product updatedProduct = productRepository.findById(productRequest.getId()).orElseThrow(()->new EntityNotFoundException("Producto no existe"));

            updatedProduct.setName(productRequest.getName());
            updatedProduct.setPrice(productRequest.getPrice());

            Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()->new EntityNotFoundException("Categoría no existe"));

            updatedProduct.setCategory(category);
            return new ResponseEntity<Product>(productRepository.save(updatedProduct), HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e) {
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
