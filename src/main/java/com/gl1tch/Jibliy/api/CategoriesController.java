package com.gl1tch.Jibliy.api;

import com.gl1tch.Jibliy.commands.CategoryCommand;
import com.gl1tch.Jibliy.dto.CategoryDTO;
import com.gl1tch.Jibliy.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping ResponseEntity<Void> createCategory(@RequestBody CategoryCommand categoryCommand, @RequestHeader("Authorization") String authHeader) {
        categoryService.createCategory(categoryCommand, authHeader);
        return ResponseEntity.ok().build();
    }

}