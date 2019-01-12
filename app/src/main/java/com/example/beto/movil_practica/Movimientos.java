package com.example.beto.movil_practica;

import android.content.ContentValues;

public class Movimientos {
    public static String TABLE_NAME = "Movimiento";
    public static String COLUMN_ID = "id_movimiento";
    public static String COLUMN_CODE = "codigo";
    public static String COLUMN_TYPE = "tipo";
    public static String COLUMN_RUT = "rut";
    public static String COLUMN_DATE = "fecha";
    public static String COLUMN_TOTAL = "total";

    //Crear tabla SQL
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CODE + " INTEGER NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL,"
            + COLUMN_RUT + " INTEGER NOT NULL,"
            + COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_TOTAL + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + COLUMN_RUT + ") " + "REFERENCES Cliente(" + COLUMN_RUT + "))";


    public ContentValues dataInsert(int codigo, String tipo, int rut, int total){
        ContentValues values = new ContentValues();
        values.put("tabla",TABLE_NAME);
        values.put(COLUMN_CODE,codigo);
        values.put(COLUMN_RUT,rut);
        values.put(COLUMN_TYPE,tipo);
        values.put(COLUMN_TOTAL,total);
        return values;

    }

    public static String selectVentas(String rut){
        return "SELECT " + COLUMN_CODE + ", "
                + COLUMN_TOTAL + " FROM " + TABLE_NAME + " WHERE " + COLUMN_RUT + " = " + rut;
    }


}
