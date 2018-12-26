package com.example.beto.movil_practica;

import android.content.ContentValues;

public class Cliente {
    public static String TABLE_NAME = "Cliente";
    public static String COLUMN_ID = "id_cliente";
    public static String COLUMN_RUT = "rut";
    public static String COLUMN_CODE = "codigo";
    public static String COLUMN_NAME = "nombre";
    public static String COLUMN_SALE = "venta";

    private int id;
    private String name;
    private int rut;
    private int code;
    private int sale;

    //Crear tabla Sql
    public static final String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "("
            + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_RUT + "INTEGER NOT NULL,"
            + COLUMN_NAME + "TEXT NOT NULL,"
            + COLUMN_CODE + "INTEGER NOT NULL,"
            + COLUMN_SALE + "INTEGER NOT NULL" + ");";


    public Cliente(int id, int rut, String name, int code, int sale) {
        this.id = id;
        this.rut = rut;
        this.name = name;
        this.code = code;
        this.sale = sale;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public ContentValues dataInsert(int rut, String name, int code, int sale){
        ContentValues values = new ContentValues();
        values.put("tabla",TABLE_NAME);
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_CODE,code);
        values.put(COLUMN_RUT,rut);
        values.put(COLUMN_SALE,sale);

        return values;
    }

}
