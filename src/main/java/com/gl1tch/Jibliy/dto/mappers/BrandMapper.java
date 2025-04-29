package com.gl1tch.Jibliy.dto.mappers;

import com.gl1tch.Jibliy.domain.Brand;
import com.gl1tch.Jibliy.dto.BrandDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(source = "file.id", target = "imageId")
    BrandDTO toDto(Brand brand);

    List<BrandDTO> toListDTO(List<Brand> brands);
}
