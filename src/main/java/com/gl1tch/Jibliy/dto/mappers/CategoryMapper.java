package com.gl1tch.Jibliy.dto.mappers;

import com.gl1tch.Jibliy.domain.Category;
import com.gl1tch.Jibliy.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toListDTO(List<Category> categories);
}