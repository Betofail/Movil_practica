package com.example.beto.movil_practica;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClienteActivity extends AppCompatActivity {


    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    public List<String> listDataHeader;
    public Button orden_nombre;
    public Button orden_deuda;
    public HashMap<String,List<String>> listHash;
    private SqlDatabaseHelper myDB = new SqlDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = findViewById(R.id._dynamic);
        orden_deuda = findViewById(R.id.button_orden_deuda);
        orden_nombre = findViewById(R.id.button_orden_nombre);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

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


    public void addClientActivity(View view){
        Intent intent = new Intent(this,AddClient.class);
        startActivity(intent);
    }

    public void initData(){
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        SQLiteDatabase db = myDB.getReadableDatabase();
        Cursor data = db.rawQuery(Cliente.SELECT_CLIENT,null);
        if (data.getCount() == 0){

        }
        else{
            int i = 0;
            while (data.moveToNext()){
                //data.getString(1) +"  "+ data.getString(2)
                listDataHeader.add(data.getString(1) + " Dueda: " + data.getString(2));
                Cursor data_ventas = myDB.ventas_client(data.getString(0));
                if (data_ventas.getCount() == 0){
                    List<String> no_data = new ArrayList<>();
                    no_data.add("No tiene ventas");
                    listHash.put(listDataHeader.get(i),no_data);
                    i += 1;
                }
                else{
                    List<String> cliente = new ArrayList<>();
                    while (data_ventas.moveToNext()){
                        cliente.add("Ventas \t\t Monto");
                        cliente.add(data_ventas.getString(0) + "\t\t" + data_ventas.getString(1) );
                    }
                    listHash.put(listDataHeader.get(i),cliente);
                    i = i +1;
                }
            }
        }
    }

    public void orden_nombre(View view){
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        SQLiteDatabase db = myDB.getReadableDatabase();
        Cursor data = db.rawQuery(Cliente.SELECT_CLIENT_NAME,null);
        if (data.getCount() == 0){

        }
        else{
            int i = 0;
            while (data.moveToNext()){
                //data.getString(1) +"  "+ data.getString(2)
                listDataHeader.add(data.getString(1) + " Dueda: " + data.getString(2));
                Cursor data_ventas = myDB.ventas_client(data.getString(0));
                if (data_ventas.getCount() == 0){
                    List<String> no_data = new ArrayList<>();
                    no_data.add("No tiene ventas");
                    listHash.put(listDataHeader.get(i),no_data);
                    i += 1;
                }
                else{
                    List<String> cliente = new ArrayList<>();
                    while (data_ventas.moveToNext()){
                        cliente.add("Ventas \t\t Monto");
                        cliente.add(data_ventas.getString(0) + "\t\t" + data_ventas.getString(1) );
                    }
                    listHash.put(listDataHeader.get(i),cliente);
                    i = i +1;
                }
            }
        }
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

    public void orden_deuda(View view){
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        SQLiteDatabase db = myDB.getReadableDatabase();
        Cursor data = db.rawQuery(Cliente.SELECT_CLIENT_DEBS,null);
        if (data.getCount() == 0){

        }
        else{
            int i = 0;
            while (data.moveToNext()){
                //data.getString(1) +"  "+ data.getString(2)
                listDataHeader.add(data.getString(1) + " Dueda: " + data.getString(2));
                Cursor data_ventas = myDB.ventas_client(data.getString(0));
                if (data_ventas.getCount() == 0){
                    List<String> no_data = new ArrayList<>();
                    no_data.add("No tiene ventas");
                    listHash.put(listDataHeader.get(i),no_data);
                    i += 1;
                }
                else{
                    List<String> cliente = new ArrayList<>();
                    while (data_ventas.moveToNext()){
                        cliente.add("Ventas \t\t Monto");
                        cliente.add(data_ventas.getString(0) + "\t\t" + data_ventas.getString(1) );
                    }
                    listHash.put(listDataHeader.get(i),cliente);
                    i = i +1;
                }
            }
        }
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }
}
