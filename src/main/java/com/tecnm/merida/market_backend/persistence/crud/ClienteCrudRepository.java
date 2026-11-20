package com.tecnm.merida.market_backend.persistence.crud;

import com.tecnm.merida.market_backend.persistence.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteCrudRepository extends CrudRepository<Cliente, String> {
}
