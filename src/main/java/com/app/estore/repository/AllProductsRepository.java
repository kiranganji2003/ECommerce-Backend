package com.app.estore.repository;

import com.app.estore.common.ProductCategory;
import com.app.estore.entity.AllProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllProductsRepository extends JpaRepository<AllProducts, Integer> {

    @Query("""
            SELECT p
            FROM AllProducts p
            WHERE p.cost BETWEEN :minCost AND :maxCost
            """)
    List<AllProducts> findProductsByCostRange(
            @Param("minCost") Integer minCost,
            @Param("maxCost") Integer maxCost
    );

    @Query("""
            SELECT ap
            FROM AllProducts ap
            WHERE ap.productCategory = :category
            """)
    List<AllProducts> findByProductCategory(@Param("category") ProductCategory category);


}
