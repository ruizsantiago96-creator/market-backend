package com.tecnm.merida.market_backend.persistence;

import com.tecnm.merida.market_backend.domain.Category;
import com.tecnm.merida.market_backend.domain.repository.CategoryRepository;
import com.tecnm.merida.market_backend.persistence.crud.CategoriaCrudRepository;
import com.tecnm.merida.market_backend.persistence.entity.Categoria;
import com.tecnm.merida.market_backend.persistence.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class CategoriaRepository implements CategoryRepository {

    private final CategoriaCrudRepository categoriaCrudRepository;
    private final CategoryMapper categoryMapper;

    public CategoriaRepository(
            CategoriaCrudRepository categoriaCrudRepository,
            CategoryMapper categoryMapper
    ) {
        this.categoriaCrudRepository = categoriaCrudRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getAll() {
        List<Categoria> categorias = StreamSupport
                .stream(categoriaCrudRepository.findAll().spliterator(), false)
                .toList();

        return categorias.stream()
                .map(categoryMapper::toCategory)
                .toList();
    }

    @Override
    public Optional<Category> getById(int categoryId) {
        return categoriaCrudRepository
                .findById(categoryId)
                .map(categoryMapper::toCategory);
    }

    @Override
    public Category save(Category category) {
        Categoria categoria = categoryMapper.toCategoria(category);

        Categoria savedCategoria = categoriaCrudRepository.save(categoria);

        return categoryMapper.toCategory(savedCategoria);
    }

    @Override
    public void delete(int categoryId) {
        categoriaCrudRepository.deleteById(categoryId);
    }
}