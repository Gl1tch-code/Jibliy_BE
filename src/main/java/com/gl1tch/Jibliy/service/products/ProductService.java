package com.gl1tch.Jibliy.service.products;

import com.gl1tch.Jibliy.domain.Product;
import com.gl1tch.Jibliy.domain.SubCategory;
import com.gl1tch.Jibliy.repository.ProductRepository;
import com.gl1tch.Jibliy.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<SubCategory> getSubCategoriesByCategoryId(Long categoryId) {
        return subCategoryRepository.findByCategory_Id(categoryId);
    }

    public List<Product> getProductsBySubCategoryId(Long subCategoryId) {
        return productRepository.findBySubCategoryId(subCategoryId);
    }

    public List<Product> getProductsBySubCategoryIdAndBrandName(Long subCategoryId, String brandName) {
        return productRepository.findBySubCategoryIdAndBrandName(subCategoryId, brandName);
    }

    public List<Product> searchProducts(String searchTerm) {
        return productRepository.searchProducts(searchTerm);
    }
}