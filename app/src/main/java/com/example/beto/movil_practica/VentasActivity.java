package com.example.beto.movil_practica;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VentasActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private TextView total;
    private EditText cliente;
    private ArrayList<Integer> total_venta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        total = findViewById(R.id.totalVenta);
        listView = findViewById(R.id.list_producto);
        list = new ArrayList<String>();
        total_venta = new ArrayList<Integer>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,list);

    }

    public void addProducto(View view){
        Intent intent = new Intent(this,PopUp.class);
        startActivityForResult(intent,123);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK){
            list.add("nombre: " + data.getStringExtra("producto")
                    + " cantidad: " + data.getStringExtra("cantidad")
                    + " precio: " + data.getStringExtra("precio"));
            listView.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
            total_venta.add(Integer.parseInt(data.getStringExtra("precio")));

            Integer sum = 0;
            for (Integer number : total_venta) {
                sum += number;
            }
            total.setText("$"+Integer.toString(sum));
        }
    }

    public void pagar(View view){

    }
}
