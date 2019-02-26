package com.example.beto.movil_practica;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductoActivity extends AppCompatActivity {

    //listas
    private ArrayList<String> list_codigo;
    private ArrayList<String> list_producto;
    private ArrayList<String> list_stock;
    private ArrayList<String> list_precio;

    private DatabaseReference productosDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        list_codigo = new ArrayList<>();
        list_producto = new ArrayList<>();
        list_stock = new ArrayList<>();
        list_precio = new ArrayList<>();

        list_codigo.add("Codigo");
        list_producto.add("Producto");
        list_stock.add("Stock");
        list_precio.add("Precio");

        RecyclerView recyclerView = findViewById(R.id.productos_agregados);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        productosDatabase = FirebaseDatabase.getInstance().getReference("Producto");

        final RecyclerViewAdapter_Product adapter_product =
                new RecyclerViewAdapter_Product(this,list_codigo,list_producto,list_stock,list_precio);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_product);

        productosDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Producto producto = snapshot.getValue(Producto.class);
                    list_codigo.add(snapshot.getKey());
                    list_producto.add(producto.getNombre());
                    list_stock.add(producto.getIngreso());
                    list_precio.add(producto.getPrecio());
                }
                adapter_product.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
