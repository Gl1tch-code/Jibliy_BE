package com.gl1tch.Jibliy.api;

import com.gl1tch.Jibliy.domain.Product;
import com.gl1tch.Jibliy.domain.SubCategory;
import com.gl1tch.Jibliy.service.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/subcategories/{categoryId}")
    public List<SubCategory> getSubCategories(@PathVariable Long categoryId) {
        return productService.getSubCategoriesByCategoryId(categoryId);
    }

    @GetMapping("/subcategory/{subCategoryId}")
    public List<Product> getProductsBySubCategory(@PathVariable Long subCategoryId) {
        return productService.getProductsBySubCategoryId(subCategoryId);
    }

    @GetMapping("/subcategory/{subCategoryId}/brand/{brandName}")
    public List<Product> getProductsBySubCategoryAndBrand(@PathVariable Long subCategoryId, @PathVariable String brandName) {
        return productService.getProductsBySubCategoryIdAndBrandName(subCategoryId, brandName);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String searchTerm) {
        return productService.searchProducts(searchTerm);
    }
}