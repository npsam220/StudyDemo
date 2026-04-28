package com.example.StudyDemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.StudyDemo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {    
    @Query(value = """
    SELECT * FROM product
    WHERE (:id IS NULL OR id = :id)
      AND (:productCode IS NULL OR product_code = :productCode)
      AND (:name IS NULL OR name LIKE CONCAT('%', :name, '%'))
      AND (:pricebegin IS NULL OR price >= :pricebegin)
      AND (:priceend IS NULL OR price <= :priceend)
    """, nativeQuery = true)
    List<Product> search(
            Long id,
            String productCode,
            String name,
            Integer pricebegin,
            Integer priceend
    ) ;

}
