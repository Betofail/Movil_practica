package com.example.beto.movil_practica;

import android.content.ContentValues;

public class Boleta_cabesera {
    private String fecha,rut,codigoDetalle;

    public Boleta_cabesera(String fecha, String rut, String codigoDetalle) {
        this.fecha = fecha;
        this.rut = rut;
        this.codigoDetalle = codigoDetalle;
    }

    public Boleta_cabesera(){
        //Default Constructor for DataSnapshot.getValue();
    }

    public String getFecha() {
        return fecha;
    }

    public String getRut() {
        return rut;
    }

    public String getCodigoDetalle() {
        return codigoDetalle;
    }
}
