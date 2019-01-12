package com.example.beto.movil_practica;

import android.content.ContentValues;

public class Producto {
    public static final String TABLE_NAME = "Producto";
    public static final String COLUMN_ID = "id_producto";
    public static final String COLUMN_CODE = "codigo";
    public static final String COLUMN_NAME = "nombre";
    public static final String COLUMN_IN = "ingreso";
    public static final String COLUMN_OUT = "egreso";
    public static final String COLUMN_PRICE = "precio";

    //Crear tabla SQL
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CODE + " INTEGER NOT NULL,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_PRICE + " INTEGER,"
            + COLUMN_IN + " INTEGER,"
            + COLUMN_OUT + " INTEGER" + ");";

    public ContentValues dataInsert(int codigo,String nombre,int ingreso,int egreso,int precio){
        ContentValues values = new ContentValues();
        values.put("tabla",TABLE_NAME);
        values.put(COLUMN_CODE,codigo);
        values.put(COLUMN_NAME,nombre);
        values.put(COLUMN_PRICE,precio);
        values.put(COLUMN_IN,ingreso);
        values.put(COLUMN_OUT,egreso);
        return values;
    }

    //Obtener data SQL
    public static final String SELECT_ACTIVITY = "SELECT " + COLUMN_CODE + ","
            + COLUMN_NAME + ","
            + COLUMN_IN + "-" + COLUMN_OUT + " AS Stock, "+COLUMN_PRICE+ " FROM "+ TABLE_NAME;
}
