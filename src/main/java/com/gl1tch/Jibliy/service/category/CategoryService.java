package com.gl1tch.Jibliy.service.category;

import com.gl1tch.Jibliy.commands.CategoryCommand;
import com.gl1tch.Jibliy.configuration.JwtTokenService;
import com.gl1tch.Jibliy.domain.Category;
import com.gl1tch.Jibliy.domain.FileEntity;
import com.gl1tch.Jibliy.domain.SubCategory;
import com.gl1tch.Jibliy.domain.User;
import com.gl1tch.Jibliy.dto.CategoryDTO;
import com.gl1tch.Jibliy.dto.mappers.CategoryMapper;
import com.gl1tch.Jibliy.repository.CategoryRepository;
import com.gl1tch.Jibliy.repository.FileRepository;
import com.gl1tch.Jibliy.repository.UserRepository;
import com.gl1tch.Jibliy.utils.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileRepository fileRepository;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.toListDTO(categoryRepository.findAll());
    }


    public void createCategory(CategoryCommand categoryCommand, String authHeader) {
        String email = jwtTokenService.extractEmail(authHeader.substring(7));

        User user = userRepository.findByEmail(email).orElseThrow();

        if (user.getRole() == RoleEnum.ADMIN) {
            FileEntity file = new FileEntity();
            if (categoryCommand.getImageId() != null) {
                file = fileRepository.findById(Long.valueOf(categoryCommand.getImageId())).orElseGet(null);
            }
            Category category = Category.builder().name(categoryCommand.getName()).file(file).build();
            categoryRepository.save(category);
        } else {
            throw new RuntimeException("Access denied");
        }
    }
}
