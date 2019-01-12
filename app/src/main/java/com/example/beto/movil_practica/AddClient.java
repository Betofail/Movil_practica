package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddClient extends AppCompatActivity {

    private SqlDatabaseHelper MyDb;
    private Cliente cliente = new Cliente();
    public EditText rut;
    public EditText name;
    public EditText cell;
    public EditText email;
    public EditText direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

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

            int rut_data = Integer.parseInt(String.valueOf(rut.getText()));
            int cell_data = Integer.parseInt(String.valueOf(cell.getText()));
            int codigo = (int) (Math.random() * 100030) + 1;
            MyDb = new SqlDatabaseHelper(this);

            ContentValues values = cliente.dataInsert(rut_data, name.getText().toString(), codigo, "Nuevo");
            MyDb.insert(values);
            Toast.makeText(this, "Insertado con Exito", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

    }

    public void home_activity(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }
}
