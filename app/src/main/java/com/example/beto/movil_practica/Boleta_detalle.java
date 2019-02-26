package com.example.beto.movil_practica;

import android.content.ContentValues;

import java.text.CollationElementIterator;

public class Boleta_detalle {
   private String codigoProducto,cantidad,monto;

    public Boleta_detalle(String codigoProducto, String cantidad, String monto) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.monto = monto;
    }

    public Boleta_detalle(){
        //Default Constructor for DataSnapshot.getValue();
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getMonto() {
        return monto;
    }
}

