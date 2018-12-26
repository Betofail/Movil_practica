package com.example.beto.movil_practica;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

public class PopUp extends Activity {
    private EditText producto;
    private EditText cantidad;
    private EditText precio;
    private String producto_data;
    private String cantidad_data;
    private String precio_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        producto = findViewById(R.id.editText_producto);
        cantidad = findViewById(R.id.editText_cantidad);
        precio = findViewById(R.id.editText_precio);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));
    }

    public void vender(View view){
        if (TextUtils.isEmpty(producto.getText().toString())){
            producto.setError("Debe ingresar un nombre");
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
