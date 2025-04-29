package com.gl1tch.Jibliy.service.subCategory;

import com.gl1tch.Jibliy.commands.CategoryCommand;
import com.gl1tch.Jibliy.configuration.JwtTokenService;
import com.gl1tch.Jibliy.domain.Category;
import com.gl1tch.Jibliy.domain.FileEntity;
import com.gl1tch.Jibliy.domain.SubCategory;
import com.gl1tch.Jibliy.domain.User;
import com.gl1tch.Jibliy.dto.CategoryDTO;
import com.gl1tch.Jibliy.dto.SubCategoryDTO;
import com.gl1tch.Jibliy.dto.mappers.SubCategoryMapper;
import com.gl1tch.Jibliy.repository.CategoryRepository;
import com.gl1tch.Jibliy.repository.FileRepository;
import com.gl1tch.Jibliy.repository.SubCategoryRepository;
import com.gl1tch.Jibliy.repository.UserRepository;
import com.gl1tch.Jibliy.utils.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;
    private final FileRepository fileRepository;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public List<SubCategoryDTO> getAll(String authHeader) {
        String email = jwtTokenService.extractEmail(authHeader.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow();
        if (user.getRole() == RoleEnum.ADMIN) {
            return subCategoryMapper.toListDTO(subCategoryRepository.findAll());
        }

        return null;

    }

    public List<SubCategoryDTO> getAllSubCategories(Long id) {
        return subCategoryMapper.toListDTO(subCategoryRepository.findAllByCategory_Id(id));
    }


    public void createSubCategory(Long categId, CategoryCommand subCategoryDTO, String authHeader) {
        String email = jwtTokenService.extractEmail(authHeader.substring(7));

        User user = userRepository.findByEmail(email).orElseThrow();
        Category category = categoryRepository.findById(categId).orElseThrow();
        if (user.getRole() == RoleEnum.ADMIN) {
            FileEntity file = new FileEntity();
            if (subCategoryDTO.getImageId() != null) {
                file = fileRepository.findById(Long.valueOf(subCategoryDTO.getImageId())).orElseGet(null);
            }
            SubCategory subCategory = SubCategory.builder().category(category).name(subCategoryDTO.getName()).file(file).build();
            subCategoryRepository.save(subCategory);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

}
