package com.example.beto.movil_practica;

import android.content.ContentValues;

public class Producto {
    public static final String TABLE_NAME = "Producto";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CODE = "codigo";
    public static final String COLUMN_NAME = "nombre";
    public static final String COLUMN_IN = "ingreso";
    public static final String COLUMN_OUT = "egreso";

    private int id;
    private int codigo;
    private String nombre;
    private int ingreso;
    private int egreso;

    //Crear tabla SQL
    public static final String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "("
            + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CODE + "INTEGER NOT NULL,"
            + COLUMN_NAME + "TEXT NOT NULL,"
            + COLUMN_IN + "INTEGER,"
            + COLUMN_OUT + "INTEGER" + ");";

    //Costructor
    public Producto(int id,int codigo,String nombre,int ingreso,int egreso){
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.ingreso = ingreso;
        this.egreso = egreso;
    }
    //geters and seters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIngreso() {
        return ingreso;
    }

    public void setIngreso(int ingreso) {
        this.ingreso = ingreso;
    }

    public int getEgreso() {
        return egreso;
    }

    public void setEgreso(int egreso) {
        this.egreso = egreso;
    }

    public ContentValues dataInsert(int codigo,String nombre,int ingreso,int egreso){
        ContentValues values = new ContentValues();
        values.put("tabla",TABLE_NAME);
        values.put(COLUMN_CODE,codigo);
        values.put(COLUMN_NAME,nombre);
        values.put(COLUMN_IN,ingreso);
        values.put(COLUMN_OUT,egreso);
        return values;
    }
}
