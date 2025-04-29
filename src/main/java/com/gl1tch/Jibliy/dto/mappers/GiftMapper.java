package com.gl1tch.Jibliy.dto.mappers;

import com.gl1tch.Jibliy.domain.Gift;
import com.gl1tch.Jibliy.dto.GiftDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GiftMapper {
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.price", target = "price")
    GiftDTO toDto(Gift gift);

    List<GiftDTO> toListDto(List<Gift> gifts);
}
