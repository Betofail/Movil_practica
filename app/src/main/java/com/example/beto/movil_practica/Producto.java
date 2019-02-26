package com.example.beto.movil_practica;

import android.content.ContentValues;

public class Producto {
    private String nombre,precio,ingreso,egreso;

    public Producto(String nombre,String precio, String ingreso, String egreso) {
        this.nombre = nombre;
        this.ingreso = ingreso;
        this.egreso = egreso;
        this.precio = precio;
    }

    public Producto(){
        //Default Constructor for DataSnapshot.getValue();
    }

    public String getNombre() {
        return nombre;
    }

    public String getIngreso() {
        return ingreso;
    }

    public String getEgreso() {
        return egreso;
    }

    public String getPrecio() {
        return precio;
    }
}
