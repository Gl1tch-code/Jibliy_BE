package com.gl1tch.Jibliy.service.product;

import com.gl1tch.Jibliy.configuration.JwtTokenService;
import com.gl1tch.Jibliy.domain.*;
import com.gl1tch.Jibliy.dto.BrandDTO;
import com.gl1tch.Jibliy.dto.ProductDTO;
import com.gl1tch.Jibliy.dto.ProductSearchDTO;
import com.gl1tch.Jibliy.dto.ProductsAndBrandsDTO;
import com.gl1tch.Jibliy.dto.mappers.BrandMapper;
import com.gl1tch.Jibliy.dto.mappers.ProductsMapper;
import com.gl1tch.Jibliy.repository.*;
import com.gl1tch.Jibliy.utils.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductsMapper productsMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public ProductsAndBrandsDTO getProductsBySubCategoryId(Long subCategoryId) {
        List<Product> products = productRepository.findBySubCategoryId(subCategoryId).orElse(new ArrayList<>());

        List<Long> uniqueBrandIdsList = products.stream()
                .map(Product::getBrandId).distinct().toList();

        List<Brand> brands = brandRepository.findAllById(uniqueBrandIdsList);

        return ProductsAndBrandsDTO.builder()
                .products(productsMapper.toListDTO(products))
                .brands(brandMapper.toListDTO(brands))
                .build();
    }

    public List<Product> getProductsBySubCategoryIdAndBrandName(Long subCategoryId, Long brandId) {
        return productRepository.findBySubCategoryIdAndBrandName(subCategoryId, brandId);
    }

    public List<ProductSearchDTO> searchProducts(String searchTerm) {
        return productsMapper.toListSearchDTO(productRepository.searchProducts(searchTerm));
    }

    public void createProduct(Long subCatId, ProductDTO productDTO, String authHeader) {
        String email = jwtTokenService.extractEmail(authHeader.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow();

        if (user.getRole() == RoleEnum.ADMIN) {
            Product product = Product.builder()
                    .name(productDTO.getName())
                    .price(productDTO.getPrice())
                    .purchasePrice(productDTO.getPurchasePrice())
                    .description(productDTO.getDescription())
                    .subCategory(subCategoryRepository.findById(subCatId).orElse(null))
                    .build();

            if (productDTO.getImageId() != null) {
                product.setFile(fileRepository.findById(Long.valueOf(productDTO.getImageId())).orElseGet(null));
            }
            if (productDTO.getBrandId() != null) {
                Brand brand = brandRepository.findById(productDTO.getBrandId()).orElseGet(null);
                product.setBrand(brand);
                brand.addProduct(product);
            }

            productRepository.save(product);

        }
    }

    public void createBrand(BrandDTO brandDTO, String authHeader) {
        String email = jwtTokenService.extractEmail(authHeader.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow();

        if (user.getRole() == RoleEnum.ADMIN) {
            Brand brand = Brand.builder()
                    .name(brandDTO.getName())
                    .build();

            if (brandDTO.getImageId() != null) {
                brand.setFile(fileRepository.findById(Long.valueOf(brandDTO.getImageId())).orElseGet(null));
            }

            brandRepository.save(brand);


        }
    }

    public List<BrandDTO> getAllBrands(String authHeader) {
        String email = jwtTokenService.extractEmail(authHeader.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow();
        if (user.getRole() == RoleEnum.ADMIN) {
            List<Brand> brands = brandRepository.findAll();
            return brandMapper.toListDTO(brands);
        }
        return null;
    }
}