package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.view.ContextThemeWrapper;

public class Compras_producto {
    private String tipo,codigoProducto,cantidad;

    public Compras_producto(String tipo, String codigoProducto, String cantidad) {
        this.tipo = tipo;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getCantidad() {
        return cantidad;
    }
}
