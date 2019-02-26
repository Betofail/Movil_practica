package com.example.beto.movil_practica;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client_list extends AppCompatActivity {

    //listas
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> debs = new ArrayList<>();
    private ArrayList<String> rut = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        initReciclerView();
    }

    public void initReciclerView(){
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_Client);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final RecyclerViewAdapter_Client adapter_client = new RecyclerViewAdapter_Client(names,debs,rut,this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());

        final DatabaseReference cliente = FirebaseDatabase.getInstance().getReference("Cliente");
        cliente.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    HashMap<String,String> cliente_nombre = (HashMap<String, String>) snapshot.getValue();
                    names.add(cliente_nombre.get("nombre"));
                    debs.add(cliente_nombre.get("deuda"));
                    rut.add(snapshot.getKey());
                }
                adapter_client.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_client);

    }
}
