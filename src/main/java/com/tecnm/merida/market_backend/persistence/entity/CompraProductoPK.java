package com.tecnm.merida.market_backend.persistence.entity;

import jakarta.persistence.Column;

import java.time.LocalDateTime;


public class CompraProductoPK {

    @Column(name = "id_compra")
    private Integer idCompra;


    @Column(name = "id_producto")
    private Integer idProducto;

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }
}
