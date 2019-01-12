package com.example.beto.movil_practica;

import android.content.ContentValues;

import java.text.CollationElementIterator;

public class Boleta_detalle {
    public static String TABLE_NAME = "Boleta_detalle";
    public static String COLUMN_ID = "id_boleta_detalle";
    public static String COLUMN_ID_BT = "id_boleta_cabesera";
    public static String COLUMN_CODE = "codigo_producto";
    public static String COLUMN_STOCK = "cantidad";
    public static String COLUMN_AMOUNT = "monto";

    private int id;
    private int codigo;
    private int codigo_BT;
    private int cantidad;
    private int monto;

    //crear tabla SQL
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CODE + " INTEGER NOT NULL,"
            + COLUMN_ID_BT + " INTEGER NOT NULL,"
            + COLUMN_STOCK + " INTEGER NOT NULL,"
            + COLUMN_AMOUNT + " INTEGER NOT NULL,"
            + "FOREIGN KEY("+ COLUMN_ID_BT + ") " +
            "REFERENCES Boleta_cabesera(" + COLUMN_ID_BT +"))";


    public ContentValues dataInsert(int codigo, int codigo_BT, int cantidad, int monto){
        ContentValues values = new ContentValues();
        values.put("tabla",TABLE_NAME);
        values.put(COLUMN_AMOUNT,monto);
        values.put(COLUMN_CODE,codigo);
        values.put(COLUMN_STOCK,cantidad);
        values.put(COLUMN_ID_BT,codigo_BT);
        return values;
    }
}

