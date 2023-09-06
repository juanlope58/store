package com.juanlopera.store.services.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juanlopera.store.dto.SaleRequestDTO;
import com.juanlopera.store.entities.Customer;
import com.juanlopera.store.entities.Product;
import com.juanlopera.store.entities.Sale;
import com.juanlopera.store.repositories.contracts.ICustomerRepository;
import com.juanlopera.store.repositories.contracts.IProductRepository;
import com.juanlopera.store.repositories.contracts.ISaleRepository;
import com.juanlopera.store.services.contracts.ISaleService;

import jakarta.persistence.EntityNotFoundException;

@Service    
public class SaleService implements ISaleService {

    @Autowired
    private ISaleRepository saleRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IProductRepository productRepository;

    @Override
    public ResponseEntity<List<Sale>> findAll() {
        try {
            return new ResponseEntity<List<Sale>>(this.saleRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Sale>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Sale> create(SaleRequestDTO saleRequest) {
        try {
            Sale sale = new Sale();

            Customer customer = customerRepository.findById(saleRequest.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

            List<Product> products = productRepository.findAllById(saleRequest.getProductIds());

            if (products.isEmpty()){
                throw new IllegalArgumentException("Debe haber al menos un producto");
            }

            sale.setCustomer(customer);
            sale.setProducts(products);


            return new ResponseEntity<Sale>(this.saleRepository.save(sale), HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (Exception e){
            return new ResponseEntity<Sale>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Sale> update(SaleRequestDTO saleRequest) {
        try {
            Sale sale = new Sale();
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

    @Override
    public ResponseEntity<List<Sale>> findByCustomerId(Long customerId) {
        try {
            Customer customer = new Customer();
            customer = this.customerRepository.findById(customerId).orElseThrow(()-> new EntityNotFoundException("El cliente no existe"));
            List<Sale> sales = customer.getSales();
            // System.out.println("Hola---------------------------------------------------------------------------------------------------------------------------------------------------------------");
            // Scanner scanner=new Scanner(System.in);
            // scanner.nextLine();
            // scanner.close();
            return new ResponseEntity<List<Sale>>(sales,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return new ResponseEntity<List<Sale>>(HttpStatus.INTERNAL_SERVER_ERROR);            
        }
    }

    @Override
    public ResponseEntity<List<Sale>> findByDate(LocalDate date) {
        try {
            List<Sale> sales = this.saleRepository.findByDate(date);
            if(sales.isEmpty()){
                throw new NoSuchElementException("No hay ventas en esta fecha");
            }
            return new ResponseEntity<List<Sale>>(sales, HttpStatus.OK);

        }catch(NoSuchElementException e){
            System.err.println("Se ha producido una excepcion: "+e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return new ResponseEntity<List<Sale>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Sale>> findByCustomerAndDateBetween(Long customerId, LocalDate date1, LocalDate date2) {
        try {
            List<Sale> sales = this.saleRepository.findByCustomerAndDateBetween(customerId, date1, date2);
            if (sales.isEmpty()){
                throw new NoSuchElementException("No hay ventas para este cliente en estas fechas");
            }
            return new ResponseEntity<List<Sale>>(sales,HttpStatus.OK);
        }catch(NoSuchElementException e){
            System.err.println("Ocurrió una excepción: "+e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
}