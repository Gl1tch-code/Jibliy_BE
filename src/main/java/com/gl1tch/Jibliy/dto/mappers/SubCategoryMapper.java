package com.gl1tch.Jibliy.dto.mappers;

import com.gl1tch.Jibliy.domain.SubCategory;
import com.gl1tch.Jibliy.dto.SubCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {
    @Mapping(source = "file.id", target = "imageId")
    SubCategoryDTO toDTO(SubCategory subCategory);

    List<SubCategoryDTO> toListDTO(List<SubCategory> subCategories);

}
