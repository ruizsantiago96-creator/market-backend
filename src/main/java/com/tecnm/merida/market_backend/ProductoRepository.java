package com.tecnm.merida.market_backend;

import com.tecnm.merida.market_backend.domain.Product;
import com.tecnm.merida.market_backend.domain.repository.ProductRepository;
import com.tecnm.merida.market_backend.persistence.crud.ProductoCrudRepository;
import com.tecnm.merida.market_backend.persistence.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class ProductoRepository implements ProductRepository {
    private final ProductoCrudRepository productoCrudRepository;
    private final ProductMapper productMapper;

    public ProductoRepository(
            ProductoCrudRepository productoCrudRepository,
            ProductMapper productMapper
    ) {
        this.productoCrudRepository = productoCrudRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> getAll() {
        return productMapper.toProducts(StreamSupport
                .stream(productoCrudRepository.findAll().spliterator(), false)
                .toList());
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        return Optional.of(productMapper.toProducts(
                productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId)
        ));
    }

    @Override
    public Optional<List<Product>> getScarceProducts(int quantity) {
        return productoCrudRepository
                .findByCantidadStockLessThanAndEstado(quantity, true)
                .map(productMapper::toProducts);
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map(productMapper::toProduct);
    }

    @Override
    public Product save(Product product) {
        return productMapper.toProduct(
                productoCrudRepository.save(productMapper.toProducto(product))
        );
    }

    @Override
    public void delete(int productId) {
        productoCrudRepository.deleteById(productId);
    }
}
