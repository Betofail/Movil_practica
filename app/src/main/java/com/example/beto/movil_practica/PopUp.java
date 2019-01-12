package com.example.beto.movil_practica;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PopUp extends Activity {
    private EditText producto;
    private EditText cantidad;
    private EditText precio;
    public TextView nombre_producto;
    private SqlDatabaseHelper helper = new SqlDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        producto = findViewById(R.id.editText_producto);
        cantidad = findViewById(R.id.editText_cantidad);
        precio = findViewById(R.id.editText_precio);
        nombre_producto = findViewById(R.id.name_product);
        final SQLiteDatabase database = helper.getReadableDatabase();

        producto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (TextUtils.isEmpty(producto.getText().toString())){
                    nombre_producto.setText("");
                }
                else {
                    if (!hasFocus) {
                        Cursor cursor = database.rawQuery
                                ("SELECT  nombre from Producto where codigo = " + producto.getText().toString(), null);
                        if (cursor.getCount() != 0) {
                            cursor.moveToNext();
                            nombre_producto.setText(cursor.getString(0));
                        } else {
                            producto.setError("El producto no existe");
                            nombre_producto.setText("");
                        }
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
