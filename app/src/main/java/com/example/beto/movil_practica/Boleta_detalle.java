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
    public static final String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "("
            + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CODE + "INTEGER NOT NULL,"
            + COLUMN_ID_BT + "INTEGER NOT NULL,"
            + COLUMN_STOCK + "INTEGER NOT NULL,"
            + COLUMN_STOCK + "INTEGER NOT NULL,"
            + "FOREIGN KEY("+ COLUMN_ID_BT + ")" +
            "REFERENCE Boleta_cabesera(" + COLUMN_ID_BT +");";

    public Boleta_detalle(int id,int codigo_BT, int codigo, int cantidad, int monto) {
        this.id = id;
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.monto = monto;
        this.codigo_BT = codigo_BT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCodigo_BT() {
        return codigo_BT;
    }

    public void setCodigo_BT(int codigo_BT) {
        this.codigo_BT = codigo_BT;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

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

