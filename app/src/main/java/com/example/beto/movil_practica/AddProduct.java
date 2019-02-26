package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddProduct extends AppCompatActivity {

    private DatabaseReference productoDatabase;
    private Producto producto;
    private EditText codigo_producto;

    ValueEventListener codigoListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                codigo_producto.setError("el Producto ya existe");
            }else{

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        codigo_producto = findViewById(R.id.save_code_product);
        codigo_producto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (TextUtils.isEmpty(codigo_producto.getText().toString())){
                    codigo_producto.setError("Ingrese un codigo para podructo");
                }else{
                    Query query = FirebaseDatabase.getInstance().getReference("Producto")
                            .orderByChild("codigo")
                            .equalTo(codigo_producto.getText().toString());
                    query.addListenerForSingleValueEvent(codigoListener);
                }
            }
        });
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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Seguro que quiere salir?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddProduct.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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
        else {
            productoDatabase = FirebaseDatabase.getInstance().getReference("Producto");
            producto = new Producto(nombre.getText().toString(),precio.getText().toString(),cantidad.getText().toString(),"0");
            String id = codigo.getText().toString();

            productoDatabase.child(id).setValue(producto);

            Toast.makeText(this, "Guardado con exito", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ProductoActivity.class);
            startActivity(intent);
        }
    }
}
