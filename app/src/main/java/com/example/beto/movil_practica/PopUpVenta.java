package com.example.beto.movil_practica;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PopUpVenta extends AppCompatActivity {

    private static final String NOMBRE_CARPETA_APP = "com.example.beto.movil_practica";
    private static final String GENERADO = "Boletas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_venta);

        Intent intent = getIntent();
        TextView monto = findViewById(R.id.monto_venta);
        TextView deuda = findViewById(R.id.monto_deuda);

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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Seguro que quiere salir?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PopUpVenta.this.finish();
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


    public void cancelar(View View){
        Intent intent = new Intent(this,VentasActivity.class);
        startActivity(intent);
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
            if (Integer.parseInt(intent.getStringExtra("deuda")) < 0 ) {
                int deuda =  - Integer.parseInt(intent.getStringExtra("deuda"));
                String monto_pagado = monto_a_pagar.getText().toString();
                String deuda_nueva = String.valueOf((Integer.parseInt(monto_a_pagar.getText().toString()) - (deuda + total)));

                int codigo = (int) (Math.random() * 9999) + 1;
                String data = "Boleta Venta  " + codigo + "\n\n" + "Cliente: " + intent.getStringExtra("rut") + "\n"
                        + "Deuda Cliente: " + intent.getStringExtra("deuda") + "\n"
                        + "Monto Venta: " + intent.getStringExtra("total") + "\n"
                        + "Monto Cancelado: " + monto_a_pagar.getText() + "\n"
                        + "Nueva Deuda: " + deuda_nueva + "\n";

                saveData(codigo, intent.getStringExtra("rut"), Integer.parseInt(monto_pagado), arrayList, deuda_nueva);
                Document document = new Document();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Boleta_" + codigo + ".pdf";
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
            else {
                String monto_pagado = monto_a_pagar.getText().toString();
                String deuda_nueva = String.valueOf((Integer.parseInt(monto_a_pagar.getText().toString()) -
                        (Integer.parseInt(intent.getStringExtra("deuda")) + total)));

                int codigo = (int) (Math.random() * 999) + 1;
                String data = "Boleta Venta  " + codigo + "\n\n" + "Cliente: " + intent.getStringExtra("rut") + "\n"
                        + "Deuda Cliente: " + intent.getStringExtra("deuda") + "\n"
                        + "Monto Venta: " + intent.getStringExtra("total") + "\n"
                        + "Monto Cancelado: " + monto_a_pagar.getText() + "\n"
                        + "Nueva Deuda: " + deuda_nueva + "\n";

                saveData(codigo, intent.getStringExtra("rut"), Integer.parseInt(monto_pagado), arrayList, deuda_nueva);

                Document document = new Document();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Boleta_" + codigo + ".pdf";
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(path));
                    document.open();
                    document.add(new Paragraph(data,FontFactory.getFont("Calibri",22)));
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
        Date currentTime = Calendar.getInstance().getTime();
        String  codigoDetalle = String.valueOf((int)(Math.random() * 9999 + 1));

        //insertar Boleta Cabesera
        //ContentValues values_cabesera = boleta_cabesera.dataInsert(Integer.parseInt(rut),codigo);
        //databaseHelper.insert(values_cabesera);
        DatabaseReference boleta_cabesera = FirebaseDatabase.getInstance().getReference("Boleta_Cabesera");
        Boleta_cabesera datosBoleta = new Boleta_cabesera(currentTime.toString(),rut,codigoDetalle);
        boleta_cabesera.child(String.valueOf(codigo)).setValue(datosBoleta);


        //insertar Boleta Detalle
        DatabaseReference boleta_detalle = FirebaseDatabase.getInstance().getReference("Boleta_Detalle");
        for (int i = 0;i<arrayList.size();i+=3){
            DatabaseReference Productos = FirebaseDatabase.getInstance().getReference("Producto").child(arrayList.get(0));
            Productos.child("egreso").setValue(arrayList.get(1));
            Boleta_detalle detalle = new Boleta_detalle(arrayList.get(0),arrayList.get(1),arrayList.get(2));
            boleta_detalle.child(codigoDetalle).setValue(detalle);
            /*databaseHelper.myDB.execSQL("UPDATE Producto SET egreso = " + arrayList.get(1) + " WHERE  codigo = " + arrayList.get(0) + ";");

            ContentValues values_detalle =
                    Boleta.dataInsert(Integer.parseInt(arrayList.get(0)),
                            codigo,Integer.parseInt(arrayList.get(1)),Integer.parseInt(arrayList.get(2)));
            databaseHelper.insert(values_detalle);*/

        }
        //insertar Movimientos de cuentas

        Movimiento movimientos = new Movimiento("pago",currentTime.toString(),String.valueOf(total),rut);
        DatabaseReference dataMovimiento = FirebaseDatabase.getInstance().getReference("Movimiento").child(String.valueOf(codigo));
        dataMovimiento.setValue(movimientos);

        //modificar Cliente segun pago
        DatabaseReference Cliente_deuda = FirebaseDatabase.getInstance().getReference("Cliente").child(rut).child("deuda");
        if (Cliente_deuda.getKey() == "Nuevo"){
            Cliente_deuda.removeValue();
        }
        DatabaseReference Cliente = FirebaseDatabase.getInstance().getReference("Cliente").child(rut).child("monto");
        Cliente.setValue(monto_pagado);
        DatabaseReference Cliente_Ventas = FirebaseDatabase.getInstance().getReference("Cliente").child(rut).child("ventas");
        Cliente_Ventas.child(String.valueOf(codigo)).setValue(true);
        Cliente_deuda.setValue(total);

        Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT).show();
    }

}
