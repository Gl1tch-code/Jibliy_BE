package com.gl1tch.Jibliy.repository;

import com.gl1tch.Jibliy.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySubCategoryId(Long subCategoryId);

    @Query("SELECT p FROM Product p JOIN p.brands b WHERE p.subCategory.id = :subCategoryId AND b.name = :brandName")
    List<Product> findBySubCategoryIdAndBrandName(@Param("subCategoryId") Long subCategoryId, @Param("brandName") String brandName);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);
}