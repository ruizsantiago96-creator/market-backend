package com.tecnm.merida.market_backend.domain;

import java.util.List;

public record PurchaseRequest(
        String idCliente,
        String medioPago,
        String comentario,
        List<Item> productosAdquiridos
) {
    public record Item(Integer idProducto, Integer cantidad, Double total) {
    }
}
