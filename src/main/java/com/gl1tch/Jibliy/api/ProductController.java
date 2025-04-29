package com.gl1tch.Jibliy.api;

import com.gl1tch.Jibliy.domain.Product;
import com.gl1tch.Jibliy.dto.BrandDTO;
import com.gl1tch.Jibliy.dto.ProductDTO;
import com.gl1tch.Jibliy.dto.ProductSearchDTO;
import com.gl1tch.Jibliy.dto.ProductsAndBrandsDTO;
import com.gl1tch.Jibliy.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/subcategory/{subCategoryId}")
    public ResponseEntity<ProductsAndBrandsDTO> getProductsBySubCategory(@PathVariable Long subCategoryId) {
        return ResponseEntity.ok(productService.getProductsBySubCategoryId(subCategoryId));
    }

    @GetMapping("/subcategory/{subCategoryId}/brand/{brandId}")
    public List<Product> getProductsBySubCategoryAndBrand(@PathVariable Long subCategoryId, @PathVariable Long brandId) {
        return productService.getProductsBySubCategoryIdAndBrandName(subCategoryId, brandId);
    }

    @GetMapping("/search")
    public List<ProductSearchDTO> searchProducts(@RequestParam String searchTerm) {
        return productService.searchProducts(searchTerm);
    }

    @PostMapping("/{subCatId}")
    public ResponseEntity<Void> createProduct(@PathVariable Long subCatId, @RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String authHeader) {
        productService.createProduct(subCatId, productDTO, authHeader);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/brand")
    public ResponseEntity<Void> createBrand(@RequestBody BrandDTO brandDTO, @RequestHeader("Authorization") String authHeader) {
        productService.createBrand(brandDTO, authHeader);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/brand")
    public ResponseEntity<List<BrandDTO>> getAllBrands(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(productService.getAllBrands(authHeader));
    }

}