package com.gl1tch.Jibliy.repository;

import com.gl1tch.Jibliy.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findProductById(Long id);

    Optional<List<Product>> findBySubCategoryId(Long subCategoryId);

    @Query("SELECT p FROM Product p WHERE p.subCategory.id = :subCategoryId AND p.brand.id = :brandId")
    List<Product> findBySubCategoryIdAndBrandName(@Param("subCategoryId") Long subCategoryId, @Param("brandId") Long brandId);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);


    @Query("SELECT p FROM Product p ORDER BY p.id")
    List<Product> findAll();
}