package com.gl1tch.Jibliy.repository;

import com.gl1tch.Jibliy.domain.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findAllByCategory_Id(Long categoryId);

    @Query("SELECT sc FROM SubCategory sc ORDER BY sc.id")
    List<SubCategory> findAll();
}