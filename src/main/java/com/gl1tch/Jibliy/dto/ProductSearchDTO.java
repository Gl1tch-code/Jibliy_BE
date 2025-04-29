package com.gl1tch.Jibliy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductSearchDTO {
    private String id;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageId;
    private String subCategoryId;
    private String subCategoryName;
}
