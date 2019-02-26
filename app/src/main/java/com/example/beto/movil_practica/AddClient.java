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
import android.util.Log;
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

import org.w3c.dom.Text;

public class AddClient extends AppCompatActivity {

    private DatabaseReference databaseCliente;
    private Cliente cliente;
    public EditText rut;
    public EditText name;
    public EditText cell;
    public EditText email;
    public EditText direction;

    ValueEventListener rutListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                rut.setError("El Cliente ya Existe");
            }
            else{

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w("Error Database" , databaseError.getDetails());

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        rut = findViewById(R.id.save_rut);
        rut.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (TextUtils.isEmpty(rut.getText().toString())){
                        rut.setError("Debe ingresar un rut");
                    }
                    else {
                        Query query = FirebaseDatabase.getInstance().getReference().child("Cliente")
                                .orderByChild("rut")
                                .equalTo(rut.getText().toString());
                        query.addListenerForSingleValueEvent(rutListener);
                    }
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
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddClient.this.finish();
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

    public void save_client(View view){
        Intent intent = new Intent(this,ClienteActivity.class);
        rut = findViewById(R.id.save_rut);
        name = findViewById(R.id.save_name);
        cell = findViewById(R.id.save_cell);
        email = findViewById(R.id.save_email);
        direction = findViewById(R.id.save_direction);

        if (TextUtils.isEmpty(rut.getText().toString())){
            rut.setError("Debe ingresar un rut");
        }
        else if (TextUtils.isEmpty(name.getText().toString())){
            name.setError("Debe ingresar un nombre");
        }
        else if (TextUtils.isEmpty(cell.getText().toString())){
            cell.setError("Debe ingresar un celular");
        }else if (TextUtils.isEmpty(email.getText().toString())){
            email.setError("Debe ingresar un Email");
        }else if (TextUtils.isEmpty(direction.getText().toString())){
            direction.setError("Debe ingresar una dirección");
        }

        else {
            databaseCliente = FirebaseDatabase.getInstance().getReference().child("Cliente");
            String id = rut.getText().toString();
            cliente = new Cliente(name.getText().toString(),"Nuevo",email.getText().toString(),null);
            databaseCliente.child(id).setValue(cliente);
            databaseCliente.child(id).child("ventas").child("none").setValue(false);
            Toast.makeText(this, "Insertado con Exito", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

    }

    public void home_activity(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }
}
