package com.tecnm.merida.market_backend.domain.repository;

import com.tecnm.merida.market_backend.domain.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {

    List<Purchase> getAll();
    Optional<List<Purchase>> getByClient(String clienteId);
    Purchase save(Purchase purchase);
}
