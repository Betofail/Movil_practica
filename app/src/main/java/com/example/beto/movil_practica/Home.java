package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void activityVentas (View view){
        final Intent intent = new Intent(this, VentasActivity.class);
        startActivity(intent);
    }

    public void activityCliente(View view){
        final Intent intent = new Intent(this,ClienteActivity.class);
        startActivity(intent);
    }

    public void activityProducto(View view){
        final Intent intent = new Intent(this,ProductoActivity.class);
        startActivity(intent);
    }

    public void activityConsulta(View view){
        //final Intent intent = new Intent(this,ConsultasActivity.class);
        //startActivity(intent);
    }
}
