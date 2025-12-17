package com.app.estore.repository;

import com.app.estore.entity.AllProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllProductsRepository extends JpaRepository<AllProducts, Integer> {
}
