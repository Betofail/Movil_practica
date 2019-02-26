package com.example.beto.movil_practica;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopUp extends Activity {
    private EditText producto;
    private EditText cantidad;
    private EditText precio;
    public TextView nombre_producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        producto = findViewById(R.id.editText_producto);
        cantidad = findViewById(R.id.editText_cantidad);
        precio = findViewById(R.id.editText_precio);
        nombre_producto = findViewById(R.id.name_product);


        producto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (TextUtils.isEmpty(producto.getText().toString())){
                    nombre_producto.setText("");
                }
                else {
                    if (!hasFocus) {
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Producto");
                        database.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(producto.getText().toString())){
                                    nombre_producto.setText(dataSnapshot.child(producto.getText().toString()).child("nombre").getValue().toString());
                                }else{
                                    producto.setError("no existe el producto");
                                    nombre_producto.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));
    }

    public void vender(View view){
        if (TextUtils.isEmpty(producto.getText().toString())){
            producto.setError("Debe ingresar un codigo");
        }
        else if (TextUtils.isEmpty(cantidad.getText().toString())){
            cantidad.setError("Debe ingresar una cantidad");
        }
        else if (TextUtils.isEmpty(precio.getText().toString())){
            precio.setError("Debe ingresar el precio");
        }
        else {
            Intent intent = new Intent(this, VentasActivity.class);
            intent.putExtra("producto", producto.getText().toString());
            intent.putExtra("cantidad", cantidad.getText().toString());
            intent.putExtra("precio", precio.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
