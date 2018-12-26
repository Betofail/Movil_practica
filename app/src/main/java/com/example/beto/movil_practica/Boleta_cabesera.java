package com.example.beto.movil_practica;

import android.content.ContentValues;

public class Boleta_cabesera {
    public static final String TABLE_NAME = "Boleta_cabesera";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RUT = "rut";
    public static final String COLUMN_CODE = "codigo";
    public static final String COLUMN_DATE = "fecha";

    private String fecha;
    private int id;
    private int rut;
    private int codigo;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CODE + "INTEGER NOT NULL,"
            + COLUMN_RUT + "INTEGER NOT NULL,"
            + COLUMN_DATE + "DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY (" + COLUMN_RUT + ")"
            + "REFERENCE Cliente (" + COLUMN_RUT + ");";

    public Boleta_cabesera(String fecha,int id,int rut,int codigo){
        this.id = id;
        this.rut = rut;
        this.codigo = codigo;
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public ContentValues dataInsert(int rut,int codigo,String fecha) {
        ContentValues values = new ContentValues();
        values.put("tabla", TABLE_NAME);
        values.put(COLUMN_CODE,codigo);
        values.put(COLUMN_RUT,rut);
        values.put(COLUMN_DATE,fecha);
        return values;
    }
}
