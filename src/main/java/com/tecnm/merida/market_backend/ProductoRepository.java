package com.tecnm.merida.market_backend;

import com.tecnm.merida.market_backend.persistence.crud.ProductoCrudRepository;
import com.tecnm.merida.market_backend.persistence.entity.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductoRepository {
private final ProductoCrudRepository productoCrudRepository;

public ProductoRepository(ProductoCrudRepository productoCrudRepository) {
    this.productoCrudRepository = productoCrudRepository;
}

//SELECT * FROM Productos
public List<Producto> getAll(){
    //Vamos a "castear"
    return (List<Producto>) productoCrudRepository.findAll();
}
}
