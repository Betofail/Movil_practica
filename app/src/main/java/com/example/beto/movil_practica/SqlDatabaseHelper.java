package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Movil_DB";

    SQLiteDatabase myDB;

    public SqlDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Boleta_cabesera.CREATE_TABLE);
        db.execSQL(Boleta_detalle.CREATE_TABLE);
        db.execSQL(Cliente.CREATE_TABLE);
        db.execSQL(Producto.CREATE_TABLE);
        db.execSQL(Compras_producto.CREATE_TABLE);
        db.execSQL(Movimientos.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public SQLiteDatabase openDB(){
        myDB = getWritableDatabase();
        return myDB;
    }
    public void closeDB( SQLiteDatabase myDB){
        if (myDB != null && myDB.isOpen()){
            myDB.close();
        }
    }
    public void insert(ContentValues values){
        myDB  = getReadableDatabase();
        String tabla = values.getAsString("tabla");
        values.remove("tabla");
        myDB.insert(tabla,null,values);
    }

    public Cursor producto_list(){
        myDB = getReadableDatabase();
        return myDB.rawQuery(Producto.SELECT_ACTIVITY,null);
    }

    public Cursor client_list(){
        myDB = getReadableDatabase();
        return myDB.rawQuery(Cliente.SELECT_CLIENT,null);
    }
    public Cursor ventas_client(String rut){
        myDB = getReadableDatabase();
        return myDB.rawQuery(Movimientos.selectVentas(rut),null);

    }
}
