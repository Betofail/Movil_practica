package com.example.beto.movil_practica;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PopUpVenta extends AppCompatActivity {

    private static final String NOMBRE_CARPETA_APP = "com.example.beto.movil_practica";
    private static final String GENERADO = "Boletas";
    public SqlDatabaseHelper databaseHelper = new SqlDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_venta);

        Intent intent = getIntent();
        TextView monto = findViewById(R.id.monto_deuda);
        TextView deuda = findViewById(R.id.monto_venta);

        monto.setText(intent.getStringExtra("total"));
        deuda.setText(intent.getStringExtra("deuda"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkPermission()) {

        } else {
            requestPermission();
        }
    }


    public void cancelar(View View){
        Toast.makeText(this, "cerrado", Toast.LENGTH_SHORT).show();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                200);
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void confirmar(View view) {
        Intent intent = getIntent();
        int total = Integer.parseInt(intent.getStringExtra("total"));
        ArrayList<String> arrayList = intent.getStringArrayListExtra("productos");
        EditText monto_a_pagar = findViewById(R.id.monto_a_pagar);
        if (TextUtils.isEmpty(monto_a_pagar.getText().toString())){
            monto_a_pagar.setError("Ingrese un monto");
        }
        else {
            String monto_pagado = monto_a_pagar.getText().toString();
            String deuda_nueva = String.valueOf((Integer.parseInt(monto_a_pagar.getText().toString()) - Integer.parseInt(intent.getStringExtra("deuda"))));

            int codigo = (int) (Math.random() * 999) + 1;
            String data = "Boleta Venta  " + codigo + "\n\n" + "Cliente: " + intent.getStringExtra("rut") + "\n"
                    + "Deuda Cliente: " + intent.getStringExtra("deuda") + "\n"
                    + "Monto Venta: " + intent.getStringExtra("total") + "\n"
                    + "Monto Cancelado: " + monto_a_pagar.getText() + "\n"
                    + "Nueva Deuda: " + deuda_nueva + "\n";

            saveData(codigo, intent.getStringExtra("rut"), total, arrayList, monto_pagado);

            Document document = new Document();
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Boleta.pdf";
            try {
                PdfWriter.getInstance(document, new FileOutputStream(path));
                document.open();
                document.add(new Paragraph(data));
                document.close();
                Toast.makeText(this, "Documento Creado", Toast.LENGTH_SHORT).show();

                mostrarPDF(path, this);

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void mostrarPDF(String archivo, Context context){
        File file = new File(archivo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri uri = FileProvider.getUriForFile(this, "com.example.beto.movil_practica.fileProvider", file);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "no tiene app para abriri PDF", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    public void saveData(int codigo, String rut,int total, ArrayList<String> arrayList,String monto_pagado){
        Boleta_detalle Boleta = new Boleta_detalle();
        Boleta_cabesera boleta_cabesera = new Boleta_cabesera();
        //insertar Boleta Cabesera
        ContentValues values_cabesera = boleta_cabesera.dataInsert(Integer.parseInt(rut),codigo);
        databaseHelper.insert(values_cabesera);

        //insertar Boleta Detalle
        for (int i = 0;i<arrayList.size();i+=3){

            databaseHelper.myDB.execSQL("UPDATE Producto SET egreso = " + arrayList.get(1) + " WHERE  codigo = " + arrayList.get(0) + ";");

            ContentValues values_detalle =
                    Boleta.dataInsert(Integer.parseInt(arrayList.get(0)),
                            codigo,Integer.parseInt(arrayList.get(1)),Integer.parseInt(arrayList.get(2)));
            databaseHelper.insert(values_detalle);
        }
        //insertar Movimientos de cuentas
        Movimientos movimientos = new Movimientos();
        ContentValues values = movimientos.dataInsert(codigo,"pago",Integer.parseInt(rut),total);
        databaseHelper.insert(values);

        //modificar Cliente segun pago
        Cliente cliente = new Cliente();
        databaseHelper.myDB.execSQL(cliente.dataUpdate(Integer.parseInt(rut),monto_pagado));

        Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT).show();
    }
}
