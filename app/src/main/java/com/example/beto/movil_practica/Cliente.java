package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.text.style.UpdateAppearance;

import java.util.ArrayList;

public class Cliente {
    private String nombre,deuda,email,ventas;

    public Cliente(String nombre, String deuda, String email, String ventas) {
        this.nombre = nombre;
        this.deuda = deuda;
        this.email = email;
        this.ventas = ventas;
    }

    public Cliente(){
        //Default Constructor for DataSnapshot.getValue();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDeuda() {
        return deuda;
    }

    public String getEmail() {
        return email;
    }

    public String getVentas() {
        return ventas;
    }
}
