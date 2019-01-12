package com.example.beto.movil_practica;

import android.content.ContentValues;

public class Boleta_cabesera {
    public static final String TABLE_NAME = "Boleta_cabesera";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RUT = "rut";
    public static final String COLUMN_CODE = "codigo";
    public static final String COLUMN_DATE = "fecha";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CODE + " INTEGER NOT NULL,"
            + COLUMN_RUT + " INTEGER NOT NULL,"
            + COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY (" + COLUMN_RUT + ") "
            + "REFERENCES Cliente(" + COLUMN_RUT + "))";


    public ContentValues dataInsert(int rut,int codigo) {
        ContentValues values = new ContentValues();
        values.put("tabla", TABLE_NAME);
        values.put(COLUMN_CODE,codigo);
        values.put(COLUMN_RUT,rut);
        return values;
    }
}
