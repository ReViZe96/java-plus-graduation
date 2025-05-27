package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;
import ru.practicum.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto saveCategory(@Valid @RequestBody NewCategoryDto dto) {
        return categoryService.saveCategory(dto);
    }

    @DeleteMapping("/{catID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("catID") Long catId) {
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catID}")
    public CategoryDto updateCategory(@PathVariable("catID") Long catId,
                                      @Valid @RequestBody CategoryDto dto) {
        return categoryService.updateCategory(catId, dto);
    }
}
