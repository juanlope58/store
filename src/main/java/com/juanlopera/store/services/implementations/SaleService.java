package com.juanlopera.store.services.implementations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

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
import com.juanlopera.store.util.Constantes;

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
            //Obtengo el cliente que está haciendo la compra
            Customer customer = customerRepository.findById(saleRequest.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado, debes registrarlo primero"));
            //Obtengo la lista de productos que van en la compra por medio de sus ids
            List<Product> products = productRepository.findAllById(saleRequest.getProductIds());
            if (products.isEmpty()){
                throw new IllegalArgumentException("Debe haber al menos un producto");
            }

            sale.setCustomer(customer);
            sale.setProducts(products);

            //Calculo el total de la venta actual
            Double totalActualPrice = 0d;
            for (Product product : products) {
                totalActualPrice += product.getPrice();
            }
            //Saco la lista de ventas que se le han realizado al cliente en los ultimos 31 días
            List<Sale> oldSales = findByCustomerAndDateBetween(saleRequest.getCustomerId(), LocalDate.now().minus(31, ChronoUnit.DAYS), LocalDate.now()).getBody();
            // Calculo el total de las ventas en los últimos 31 días
            Double totalOldPrice = 0d;
            for (Sale sale2 : oldSales) {
                for (Product product : sale2.getProducts()) {
                    totalOldPrice += product.getPrice(); 
                }
            }
            // Aplico descuento si el cliente ha hecho compras mayores a un millón
            if((totalActualPrice+totalOldPrice)>1000000){
                System.out.println("Obtiene un descuento por compras mayores a un millón");
                System.out.println("Valor a pagar: " + (totalActualPrice-(totalActualPrice*Constantes.DESCUENTO_CLIENTE_FIEL)));

            //Llamo el juego para ver si hay descuento.
            }else if(discountGame()){
                System.out.println("Se ha ganado un descuento");
                System.out.println("Valor a pagar: "+ (totalActualPrice-totalActualPrice*Constantes.DESCUENTO_JUEGO));
            }else{
                System.out.println("Valor a pagar: "+totalActualPrice);
            }
            
            return new ResponseEntity<Sale>(this.saleRepository.save(sale), HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            System.err.println("Se encontró una excepción: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            
        }catch (IllegalArgumentException e){
            System.err.println("Se encontró una excepción: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (Exception e){
            return new ResponseEntity<Sale>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Sale> update(SaleRequestDTO saleRequest) {
        try {
            Sale sale = saleRepository.findById(saleRequest.getId()).orElseThrow(()-> new EntityNotFoundException("No se encuantra la venta con el id entregado"));

            Customer customer = customerRepository.findById(saleRequest.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
            
            //Obtengo la lista de productos que van en la compra por medio de sus ids
            List<Product> products = productRepository.findAllById(saleRequest.getProductIds());
            if (products.isEmpty()){
                throw new IllegalArgumentException("Debe haber al menos un producto");
            }
            sale.setCustomer(customer);
            sale.setProducts(products);
            return new ResponseEntity<Sale>(this.saleRepository.save(sale) ,HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            System.err.println("Se encontró una excepción: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            
        }catch (IllegalArgumentException e){
            System.err.println("Se encontró una excepción: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (Exception e){
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
    
    private Boolean discountGame(){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int numIntentos=1;
        Boolean win=false;
        do {
            System.out.println("Preparado para ganar? Intento nro. "+numIntentos);
            scanner.nextLine();
            int option=random.nextInt(3)+1;
            switch (option) {
                case 1:
                    win = true;
                    return win;
                case 2:
                    numIntentos++;
                    break;
                case 3:
                    System.out.println("No ganó el descuento");
                    return win;
                default:
                    break;
            }
        } while (numIntentos<=3);        
        scanner.close();
        return win;

    }
}