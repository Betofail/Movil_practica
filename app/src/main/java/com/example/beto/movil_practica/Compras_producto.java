package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.view.ContextThemeWrapper;

public class Compras_producto {
    public static final String TABLE_NAME = "Compras_producto";
    public static final String COLUMN_ID = "id_compras";
    public static final String COLUMN_CODE = "codigo";
    public static final String COLUMN_TYPE = "tipo";
    public static final String COLUMN_CODE_PRODUCT = "codigo_producto";
    public static final String COLUMN_STOCK = "cantidad";

    private int id;
    private int codigo;
    private String tipo;
    private int codigo_producto;
    private int cantidad;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CODE + " INTEGER NOT NULL,"
            + COLUMN_TYPE + " TEXT NOT NULL,"
            + COLUMN_CODE_PRODUCT + " INTEGER NOT NULL,"
            + COLUMN_STOCK + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + COLUMN_CODE_PRODUCT + ") "
            + "REFERENCES Producto (" + COLUMN_CODE + "))";


    public ContentValues dataInsert(int codigo, String tipo, int codigo_producto, int cantidad){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CODE,codigo);
        values.put(COLUMN_CODE_PRODUCT,codigo_producto);
        values.put(COLUMN_STOCK,cantidad);
        values.put(COLUMN_TYPE,tipo);
        return values;
    }
}
