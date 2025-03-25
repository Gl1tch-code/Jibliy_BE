package com.gl1tch.Jibliy.api;

import com.gl1tch.Jibliy.domain.Category;
import com.gl1tch.Jibliy.dto.CategoryDTO;
import com.gl1tch.Jibliy.dto.mappers.CategoryMapper;
import com.gl1tch.Jibliy.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toListDTO(categories);
    }
}