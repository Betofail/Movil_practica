package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {

    private SqlDatabaseHelper helper = new SqlDatabaseHelper(this);
    private Producto producto = new Producto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.to_home:
                home_activity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void home_activity(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }

    public void saveProduct(View view){
        EditText codigo = findViewById(R.id.save_code_product);
        EditText nombre = findViewById(R.id.save_name_product);
        EditText cantidad = findViewById(R.id.save_stock_product);
        EditText precio = findViewById(R.id.save_price_product);

        if (TextUtils.isEmpty(codigo.getText().toString())){
            codigo.setError("debe ingresar un codigo");
        }
        else if (TextUtils.isEmpty(nombre.getText().toString())){
            nombre.setError("debe ingresar un producto");
        }
        else if (TextUtils.isEmpty(cantidad.getText().toString())){
            cantidad.setError("debe ingresar una cantidad, si no tiene ponga 0");
        }
        else if (TextUtils.isEmpty(precio.getText().toString())){
            precio.setError("debe ingresar el precio");
        }
        else{
            ContentValues values =  producto.dataInsert(Integer.parseInt(codigo.getText().toString()),nombre.getText().toString(),
                    Integer.parseInt(cantidad.getText().toString()),0,Integer.parseInt(precio.getText().toString()));
            helper.insert(values);
            Toast.makeText(this, "Guardado con exito", Toast.LENGTH_SHORT).show();
        }
    }
}
