package com.gl1tch.Jibliy.dto.mappers;

import com.gl1tch.Jibliy.domain.FileEntity;
import com.gl1tch.Jibliy.dto.BannerDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    BannerDTO toBannerDTO(FileEntity fileEntity);

    List<BannerDTO> toBannerListDTO(List<FileEntity> fileEntities);

}
