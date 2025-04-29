package com.gl1tch.Jibliy.dto.mappers;

import com.gl1tch.Jibliy.domain.Product;
import com.gl1tch.Jibliy.dto.ProductDTO;
import com.gl1tch.Jibliy.dto.ProductSearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductsMapper {
    @Mapping(source = "file.id", target = "imageId")
    @Mapping(source = "brand.id", target = "brandId")
    ProductDTO toDTO(Product product);

    @Mapping(source = "file.id", target = "imageId")
    @Mapping(source = "subCategory.id", target = "subCategoryId")
    @Mapping(source = "subCategory.name", target = "subCategoryName")
    ProductSearchDTO toSearchDTO(Product product);

    List<ProductSearchDTO> toListSearchDTO(List<Product> products);

    List<ProductDTO> toListDTO(List<Product> products);
}
