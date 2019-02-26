package com.example.beto.movil_practica;

import android.content.ContentValues;

public class Movimiento {
    private String tipo,fecha,total,rut;

    public Movimiento(String tipo, String fecha, String total,String rut) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.total = total;
        this.rut = rut;
    }

    public Movimiento(){
        //Default Constructor for DataSnapshot.getValue();
    }

    public String getTipo() {
        return tipo;
    }

    public String getRut(){
        return  rut;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTotal() {
        return total;
    }

}
