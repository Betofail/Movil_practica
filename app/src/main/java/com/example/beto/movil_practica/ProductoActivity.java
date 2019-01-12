package com.example.beto.movil_practica;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductoActivity extends AppCompatActivity {

    private ArrayList<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;
    private SqlDatabaseHelper myDB = new SqlDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
    }

    @Override
    protected void onStart() {
        super.onStart();
        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,list);
        listView = findViewById(R.id.productos_agregados);
        list.add("Codigo    Producto    Stock   Precio UNI");
        Cursor data = myDB.producto_list();
        if (data.getCount() == 0){
            Toast.makeText(this, "No posee datos para mostrar", Toast.LENGTH_SHORT).show();
            listView.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

        }else{
            while(data.moveToNext()){
                list.add(data.getString(0) + "\t" + data.getString(1)
                        +"\t"+ data.getString(2) + "\t" + data.getString(3));
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        }

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


    public void addProductActivity(View view){
        Intent intent = new Intent(this,AddProduct.class);
        startActivity(intent);
    }
    public void buyProductActivity(View view){
        Intent intent = new Intent(this,BuyProduct.class);
        startActivity(intent);
    }
}
