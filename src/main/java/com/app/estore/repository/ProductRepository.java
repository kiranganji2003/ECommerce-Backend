package com.app.estore.repository;

import com.app.estore.entity.Product;
import com.app.estore.utility.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.cost BETWEEN :minCost AND :maxCost
            """)
    List<Product> findProductsByCostRange(
            @Param("minCost") Integer minCost,
            @Param("maxCost") Integer maxCost
    );

    @Query("""
            SELECT p
            FROM Product p
            WHERE p.productCategory = :category
            """)
    List<Product> findByProductCategory(@Param("category") ProductCategory category);

}
