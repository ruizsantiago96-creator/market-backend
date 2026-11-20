package com.tecnm.merida.market_backend.domain.service;

import com.tecnm.merida.market_backend.domain.Category;
import com.tecnm.merida.market_backend.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    public Optional<Category> getById(int categoryId) {
        return categoryRepository.getById(categoryId);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> update(
            int categoryId,
            Category category
    ) {
        return categoryRepository.getById(categoryId)
                .map(existingCategory -> {
                    existingCategory.setCategoryId(categoryId);
                    existingCategory.setCategory(category.getCategory());
                    existingCategory.setActive(category.isActive());

                    return categoryRepository.save(existingCategory);
                });
    }

    public boolean delete(int categoryId) {
        if (categoryRepository.getById(categoryId).isEmpty()) {
            return false;
        }

        categoryRepository.delete(categoryId);
        return true;
    }
}