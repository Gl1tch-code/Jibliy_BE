package com.gl1tch.Jibliy.service.file;

import com.gl1tch.Jibliy.domain.FileEntity;
import com.gl1tch.Jibliy.dto.BannerDTO;
import com.gl1tch.Jibliy.dto.mappers.FileMapper;
import com.gl1tch.Jibliy.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileUploadService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileMapper fileMapper;

    public FileEntity storeFile(MultipartFile file) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());

        return fileRepository.save(fileEntity);
    }

    public FileEntity storeBannerImg(MultipartFile file, String subCatId, String subCatName) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setBanner(true);
        fileEntity.setSubCatId(subCatId);
        fileEntity.setSubCatName(subCatName);

        return fileRepository.save(fileEntity);
    }

    public FileEntity getFile(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
    }
    public List<BannerDTO> getBanners() {
        List<FileEntity> banners = fileRepository.findAllByBanner().orElseThrow(
                () -> new RuntimeException("No banners found"));

        return fileMapper.toBannerListDTO(banners);

    }
}
