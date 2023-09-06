package com.juanlopera.store.repositories.contracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

import com.juanlopera.store.entities.Sale;

public interface ISaleRepository extends JpaRepository<Sale,Long> {
    //Consulta usando la convención de nombres de Spring Data JPA
    List<Sale> findByDate(LocalDate date);

    // Consulta personalizada usando un Query manualmente usando JPQL
    // @Query("SELECT s FROM Sale s WHERE s.date = :date")
    // List<Sale> findByDate(@Param("date") LocalDate date);
}
