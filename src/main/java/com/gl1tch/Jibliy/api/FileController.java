package com.gl1tch.Jibliy.api;

import com.gl1tch.Jibliy.domain.FileEntity;
import com.gl1tch.Jibliy.dto.BannerDTO;
import com.gl1tch.Jibliy.service.file.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileUploadService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileEntity fileEntity = fileStorageService.storeFile(file);
            return ResponseEntity.ok(fileEntity.getId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed");
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        FileEntity fileEntity = fileStorageService.getFile(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                .body(fileEntity.getData());
    }

    @PostMapping("/upload/banner")
    public ResponseEntity<Object> uploadBannerImage(@RequestParam("file") MultipartFile file,
                                                    @RequestParam String subCatId,
                                                    @RequestParam String subCatName) {
        try {
            FileEntity fileEntity = fileStorageService.storeBannerImg(file, subCatId, subCatName);
            return ResponseEntity.ok(fileEntity.getId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed");
        }
    }

    @GetMapping("/banners")
    public ResponseEntity<List<BannerDTO>> getBanners() {
        return ResponseEntity.ok(fileStorageService.getBanners());
    }
}
