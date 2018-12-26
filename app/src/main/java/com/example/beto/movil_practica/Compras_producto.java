package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.view.ContextThemeWrapper;

public class Compras_producto {
    public static final String TABLE_NAME = "Compras_producto";
    public static final String COLUMN_ID = "id";
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
            + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CODE + "INTEGER NOT NULL,"
            + COLUMN_TYPE + "TEXT NOT NULL,"
            + COLUMN_CODE_PRODUCT + "INTEGER NOT NULL,"
            + COLUMN_STOCK + "INTEGER NOT NULL,"
            + "FOREIGN KEY (" + COLUMN_CODE_PRODUCT + ")"
            + "REFERENCE Producto (" + COLUMN_CODE + ");";

    public Compras_producto(int id, int codigo, String tipo, int codigo_producto, int cantidad) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.codigo_producto = codigo_producto;
        this.cantidad = cantidad;
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

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(int codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ContentValues dataInsert(int codigo, String tipo, int codigo_producto, int cantidad){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CODE,codigo);
        values.put(COLUMN_CODE_PRODUCT,codigo_producto);
        values.put(COLUMN_STOCK,cantidad);
        values.put(COLUMN_TYPE,tipo);
        return values;
    }
}
