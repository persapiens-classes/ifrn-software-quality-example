package org.persapiens.account.controller;

import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.domain.Category;
import org.persapiens.account.service.CategoryService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController extends CrudController<CategoryDTO, Category, Long> {

    @Autowired
    private CategoryService categoryService;

    @Override
    protected Category toEntity(CategoryDTO dto) {
        return Category.builder()
           .description(dto.getDescription())
           .build();
    }

    @Override
    protected CategoryDTO toDTO(Category entity) {
        return CategoryDTO.builder()
           .description(entity.getDescription())
           .build();
    }
    
    @GetMapping("/findByDescription")
    public Optional<CategoryDTO> findByDescription(@RequestParam String description) {
        return toDTOOptional(categoryService.findByDescription(description));
    }

}