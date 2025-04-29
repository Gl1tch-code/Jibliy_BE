package com.gl1tch.Jibliy.dto;

import com.gl1tch.Jibliy.domain.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductsAndBrandsDTO {
    public List<ProductDTO> products;
    public List<BrandDTO> brands;
}
