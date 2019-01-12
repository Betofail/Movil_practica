package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.text.style.UpdateAppearance;

public class Cliente {
    public static String TABLE_NAME = "Cliente";
    public static String COLUMN_ID = "id_cliente";
    public static String COLUMN_RUT = "rut";
    public static String COLUMN_PHONE = "telefono";
    public static String COLUMN_CODE = "codigo";
    public static String COLUMN_NAME = "nombre";
    public static String COLUMN_SALE = "venta";

    //Crear tabla Sql
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_RUT + " TEXT NOT NULL,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_CODE + " INTEGER NOT NULL,"
            + COLUMN_SALE + " TEXT NOT NULL" + ");";

    public static final String SELECT_CLIENT = "SELECT " + COLUMN_RUT + ", " + COLUMN_NAME +", "
            + COLUMN_SALE + " FROM " + TABLE_NAME;

    public static  final String SELECT_CLIENT_NAME = "SELECT " + COLUMN_RUT + ", " + COLUMN_NAME +", "
            + COLUMN_SALE + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAME + " ASC";

    public static final String SELECT_CLIENT_DEBS = "SELECT " + COLUMN_RUT + ", " + COLUMN_NAME +", "
            + COLUMN_SALE + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_SALE + " ASC";

    public String dataUpdate(int rut, String valor){
        String UPDATE = "UPDATE " + TABLE_NAME + " SET " + COLUMN_SALE + " = " + valor
                + " WHERE rut = " + rut;
        return UPDATE;
    }



    public ContentValues dataInsert(int rut, String name, int code, String sale){
        ContentValues values = new ContentValues();
        values.put("tabla",TABLE_NAME);
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_CODE,code);
        values.put(COLUMN_RUT,rut);
        values.put(COLUMN_SALE,sale);

        return values;
    }

}
