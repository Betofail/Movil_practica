package com.example.beto.movil_practica;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VentasActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> list;
    private ArrayList<String> lista_productos;
    private ArrayAdapter<String> arrayAdapter;
    private TextView total;
    private TextView monto_deuda;
    private TextView cliente;
    private ArrayList<Integer> total_venta;
    private String rut_cliente;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        total = findViewById(R.id.totalVenta);
        listView = findViewById(R.id.list_producto);
        list = new ArrayList<String>();
        TextView textView = findViewById(R.id.client_name_text);
        lista_productos = new ArrayList<>();
        total_venta = new ArrayList<Integer>();
        arrayAdapter = new ArrayAdapter<String>(VentasActivity.this,android.R.layout.simple_list_item_activated_1,list);
        monto_deuda = findViewById(R.id.monto_deuda_cliente);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){

        }else{
            textView.setText(bundle.getString("nombre"));
            monto_deuda.setText(bundle.getString("deuda"));
            rut_cliente = bundle.getString("rut");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int posicion = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(VentasActivity.this);
                builder.setMessage("Desea borrar el producto?").setCancelable(false)
                .setPositiveButton("si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(posicion);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            AlertDialog alert = builder.create();
            alert.setTitle("Borrar");
            alert.show();
            }
        });
    }

    public void home_activity(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }

    public void addProducto(View view){
        Intent intent = new Intent(this,PopUp.class);
        startActivityForResult(intent,123);
    }

    public void clientList(View view){
        Intent intent = new Intent(this,Client_list.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK){
            lista_productos.add(data.getStringExtra("producto"));
            lista_productos.add(data.getStringExtra("cantidad"));
            lista_productos.add(data.getStringExtra("precio"));

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
            total.setText(Integer.toString(sum));
        }
    }


    public void pagar(View view){
        cliente = findViewById(R.id.client_name_text);
        monto_deuda = findViewById(R.id.monto_deuda_cliente);
        if (TextUtils.isEmpty(cliente.getText().toString())){
            Toast.makeText(this, "Ingrese un cliente", Toast.LENGTH_SHORT).show();
        }
        else if (total.getText().toString().equals("0") || total.getText().toString().equals("$0")){
            Toast.makeText(this, "ingrese productos para realizar la venta", Toast.LENGTH_SHORT).show();
        }
        else if (monto_deuda.getText().toString().equals("no existe el cliente")){
            Toast.makeText(this, "ingrese un cliente correcto", Toast.LENGTH_SHORT).show(); 
        }
        else {
            if (monto_deuda.getText().toString().equals("Nuevo")){
                String deuda = "0";
                Intent intent  = new Intent(this,PopUpVenta.class);
                intent.putExtra("total",total.getText().toString());
                intent.putExtra("rut", rut_cliente);
                intent.putExtra("deuda",deuda);
                intent.putExtra("productos",lista_productos);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, PopUpVenta.class);
                intent.putExtra("total", total.getText().toString());
                intent.putExtra("rut",rut_cliente);
                intent.putExtra("deuda", monto_deuda.getText().toString());
                intent.putExtra("productos", lista_productos);
                startActivity(intent);
            }
        }

    }


}
