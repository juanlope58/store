package com.juanlopera.store.repositories.contracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.juanlopera.store.entities.Sale;

public interface ISaleRepository extends JpaRepository<Sale,Long> {
    //Consulta usando la convención de nombres de Spring Data JPA
    List<Sale> findByDate(LocalDate date);

    // Consulta personalizada usando un Query manualmente usando JPQL
    @Query("SELECT s FROM Sale s WHERE s.customer.id = :customerId AND s.date BETWEEN :date1 AND :date2")
    List<Sale> findByCustomerAndDateBetween(@Param("customerId") Long customerId, @Param("date1") LocalDate date1, @Param("date2") LocalDate date2);
}
