package com.gl1tch.Jibliy.api;

import com.gl1tch.Jibliy.commands.CategoryCommand;
import com.gl1tch.Jibliy.dto.CategoryDTO;
import com.gl1tch.Jibliy.dto.SubCategoryDTO;
import com.gl1tch.Jibliy.service.subCategory.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub-categories")
public class SubCategoriesController {

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping
    public ResponseEntity<List<SubCategoryDTO>> getAll(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(subCategoryService.getAll(authHeader));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SubCategoryDTO>> getCategories(@PathVariable Long id) {
        return ResponseEntity.ok(subCategoryService.getAllSubCategories(id));
    }

    @PostMapping("/{categId}")
    ResponseEntity<Void> createSubCategory(@PathVariable Long categId, @RequestBody CategoryCommand subCategoryDTO, @RequestHeader("Authorization") String authHeader) {
        subCategoryService.createSubCategory(categId, subCategoryDTO, authHeader);
        return ResponseEntity.ok().build();
    }

}
