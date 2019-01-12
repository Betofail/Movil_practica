package com.example.beto.movil_practica;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BuyProduct extends AppCompatActivity {

    private SqlDatabaseHelper helper = new SqlDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);
        final EditText codigo = findViewById(R.id.buy_code);
        codigo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    SQLiteDatabase database = helper.getReadableDatabase();
                    Cursor cursor = database.rawQuery
                            ("SELECT  * from Producto where codigo = " + codigo.getText().toString(), null);
                    if (cursor.getCount() != 0) {

                    } else {
                        codigo.setError("El producto no existe");
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

    public void home_activity(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }

    public void buyProduct(View view) {
        EditText codigo = findViewById(R.id.buy_code);
        EditText stock = findViewById(R.id.buy_stock);
        EditText precio = findViewById(R.id.buy_price);


        if (TextUtils.isEmpty(codigo.getText().toString())){
            codigo.setError("debe ingresar un codigo");
        }
        else if (TextUtils.isEmpty(stock.getText().toString())){
            stock.setError("debe ingresar una cantidad, si no tiene ponga 0");
        }
        else if (TextUtils.isEmpty(precio.getText().toString())){
            precio.setError("debe ingresar el precio");
        }
        else{

            SQLiteDatabase myDB = helper.getReadableDatabase();
            Cursor cursor =  myDB.rawQuery("SELECT ingreso FROM Producto WHERE codigo = " + codigo.getText().toString(),null);
            if (cursor.getCount() == 0){

            }
            else{
                while (cursor.moveToNext()){
                    myDB.execSQL("UPDATE Producto SET ingreso = " + stock.getText().toString()+ " + " + cursor.getString(0) +
                            " WHERE codigo  = " + codigo.getText().toString() + ";");
                    Toast.makeText(this, "Compra realizada", Toast.LENGTH_SHORT).show();
                }
                codigo.setText("");
                stock.setText("");
                precio.setText("");
            }
        }
    }
}
