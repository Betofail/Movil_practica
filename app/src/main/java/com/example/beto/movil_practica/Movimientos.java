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

    private int id;
    private int codigo;
    private String tipo;
    private int rut;
    private String fecha;
    private int total;

    //Crear tabla SQL
    public static final String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "("
            + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CODE + "INTEGER NOT NULL,"
            + COLUMN_TYPE + "TEXT NOT NULL,"
            + COLUMN_RUT + "INTEGER NOT NULL,"
            + COLUMN_DATE + "DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_TOTAL + "INTEGER NOT NULL,"
            + "FOREIGN KEY (" + COLUMN_RUT + ")" + "REFERENCE Cliente(" + COLUMN_RUT + ");";

    //Constructor
    public Movimientos(int id, int codigo, String tipo, int rut, String fecha, int total){
        this.codigo = codigo;
        this.id = id;
        this.tipo = tipo;
        this.rut = rut;
        this.fecha = fecha;
        this.total = total;
    }

    //Geters and Seters
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

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ContentValues dataInsert(int codigo, String tipo, int rut, String fecha, int total){
        ContentValues values = new ContentValues();
        values.put("tabla",TABLE_NAME);

        values.put(COLUMN_CODE,codigo);
        values.put(COLUMN_DATE,fecha);
        values.put(COLUMN_RUT,rut);
        values.put(COLUMN_CODE,codigo);
        values.put(COLUMN_TYPE,tipo);
        values.put(COLUMN_TOTAL,total);
        return values;

    }


}
