package com.tecnm.merida.market_backend.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable

public class CompraProductoPK implements Serializable {

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

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof CompraProductoPK key
                && Objects.equals(idCompra, key.idCompra)
                && Objects.equals(idProducto, key.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompra, idProducto);
    }
}
