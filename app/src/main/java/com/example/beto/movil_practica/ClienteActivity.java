package com.example.beto.movil_practica;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteActivity extends AppCompatActivity {


    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    public List<String> listDataHeader;
    public Button orden_nombre;
    public Button orden_deuda;
    public HashMap<String,List<String>> listHash;


    public ClienteActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        listView = findViewById(R.id._dynamic);
        orden_deuda = findViewById(R.id.button_orden_deuda);
        orden_nombre = findViewById(R.id.button_orden_nombre);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkPermission()) {

        } else {
            requestPermission();
        }
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

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference clientes = ref.child("Cliente");

        clientes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                   Map<String,String> cliente = (Map<String, String>) snapshot.getValue();
                    listDataHeader.add(cliente.get("nombre") + " deuda: " + cliente.get("deuda"));
                    final DataSnapshot ventas = snapshot.child("ventas");
                    final DatabaseReference movimiento = ref.child("Movimiento");
                    final int finalI = i;
                    movimiento.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<String> datos = new ArrayList<>();
                            datos.add("Venta" + "\t\t\t" + "Monto");
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                                Movimiento movimientos = snapshot1.getValue(Movimiento.class);
                                if (ventas.hasChild(snapshot1.getKey())){
                                    datos.add(snapshot1.getKey()+"\t\t\t"+movimientos.getTotal());
                                    listHash.put(listDataHeader.get(finalI),datos);
                                }
                            }
                            if (datos.size() == 1){
                                datos.clear();
                                datos.add("no tiene ventas");
                                listHash.put(listDataHeader.get(finalI),datos);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    listAdapter.notifyDataSetChanged();
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                generarPDF(groupPosition,childPosition);
                return true;
            }
        });
    }

    public void orden_nombre(View view){
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query clientes = ref.child("Cliente").orderByChild("nombre");

        clientes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Map<String,String> cliente = (Map<String, String>) snapshot.getValue();
                    listDataHeader.add(cliente.get("nombre") + " deuda: " + cliente.get("deuda"));
                    final DataSnapshot ventas = snapshot.child("ventas");
                    final DatabaseReference movimiento = ref.child("Movimiento");
                    final int finalI = i;
                    movimiento.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<String> datos = new ArrayList<>();
                            datos.add("Venta" + "\t\t\t" + "Monto");
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                                Movimiento movimientos = snapshot1.getValue(Movimiento.class);
                                if (ventas.hasChild(snapshot1.getKey())){
                                    datos.add(snapshot1.getKey()+"\t\t\t"+movimientos.getTotal());
                                    listHash.put(listDataHeader.get(finalI),datos);
                                }
                            }
                            if (datos.size() == 1) {
                                datos.clear();
                                datos.add("no tiene ventas");
                                listHash.put(listDataHeader.get(finalI),datos);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    listAdapter.notifyDataSetChanged();
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);


        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                generarPDF(groupPosition,childPosition);
                return true;
            }
        });

    }

    public void orden_deuda(View view){
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query clientes = ref.child("Cliente").orderByChild("deuda");

        clientes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Map<String,String> cliente = (Map<String, String>) snapshot.getValue();
                    listDataHeader.add(cliente.get("nombre") + " deuda: " + cliente.get("deuda"));
                    final DataSnapshot ventas = snapshot.child("ventas");
                    final DatabaseReference movimiento = ref.child("Movimiento");
                    final int finalI = i;
                    movimiento.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<String> datos = new ArrayList<>();
                            datos.add("Venta" + "\t\t\t" + "Monto");
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                                Movimiento movimientos = snapshot1.getValue(Movimiento.class);
                                if (ventas.hasChild(snapshot1.getKey())){
                                    datos.add(snapshot1.getKey()+"\t\t\t"+movimientos.getTotal());
                                    listHash.put(listDataHeader.get(finalI),datos);
                                }
                            }
                            if (datos.size() == 1){
                                datos.clear();
                                datos.add("no tiene ventas");
                                listHash.put(listDataHeader.get(finalI),datos);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    listAdapter.notifyDataSetChanged();
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                generarPDF(groupPosition,childPosition);
                return true;
            }
        });
    }

   public void generarPDF(int grupo, int posicion){
        if (posicion == 0) {

        }
        else {
            Object datos = listAdapter.getChild(grupo, posicion);
            String[] partes = datos.toString().split("\t\t\t");
            final String codigo = partes[0];
            final String[] rut = {""};
            final String[] codigoDetalle = {""};
            final String[] total = {""};
            final String[] deuda = {""};
            final String[] venta = {""};

            //obtencion de datos de Boleta_Cabesera
            DatabaseReference BoletaCabesera = FirebaseDatabase.getInstance().getReference("Boleta_Cabesera").child(codigo);
            BoletaCabesera.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Boleta_cabesera boleta = dataSnapshot.getValue(Boleta_cabesera.class);
                    rut[0] = boleta.getRut();
                    codigoDetalle[0] = boleta.getCodigoDetalle();
                    Log.d("BoletaCabesera", "onDataChange: rut:" + rut[0] +" codigo: " + codigoDetalle[0]);

                    //obtencion de datos de Boleta Detalle
                    DatabaseReference BoletaDetalle = FirebaseDatabase.getInstance().getReference("Boleta_Detalle");
                    BoletaDetalle.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int sumaTotal = 0;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                if (snapshot.getKey() == codigoDetalle[0]) {
                                    Boleta_detalle detalle = snapshot.getValue(Boleta_detalle.class);
                                    sumaTotal = Integer.parseInt(detalle.getMonto() + sumaTotal);
                                }
                                else{

                                }
                            }
                            total[0] = String.valueOf(sumaTotal);

                            //obtencion de datos del cliente
                            DatabaseReference cliente = FirebaseDatabase.getInstance().getReference("Cliente").child(rut[0]);
                            cliente.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Map<String,String> cliente = (Map<String,String>)dataSnapshot.getValue();
                                    deuda[0] = cliente.get("deuda");

                                    //obtencion de datos del movimiento de ventas
                                    DatabaseReference movimiento = FirebaseDatabase.getInstance().getReference("Movimiento").child(codigo);
                                    movimiento.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Movimiento datos = dataSnapshot.getValue(Movimiento.class);
                                            venta[0] = datos.getTotal();

                                            //generacion de boleta en formato pdf
                                            final String data = "Boleta Venta  " + codigo + "\n\n" + "Cliente: " + rut[0] + "\n"
                                                    + "Deuda Cliente: " + deuda[0] + "\n"
                                                    + "Monto Venta: " + total[0] + "\n"
                                                    + "Monto Cancelado: " + venta[0] + "\n";

                                            Document document = new Document();
                                            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Boleta_" + codigo + ".pdf";
                                            try {
                                                PdfWriter.getInstance(document, new FileOutputStream(path));
                                                document.open();
                                                document.add(new Paragraph(data, FontFactory.getFont("Calibri",22)));
                                                document.close();
                                                Toast.makeText(ClienteActivity.this, "Documento Creado", Toast.LENGTH_SHORT).show();

                                                mostrarPDF(path, ClienteActivity.this);

                                            } catch (DocumentException e) {
                                                e.printStackTrace();
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d("Cliente Tabla", "onCancelled: no guardo ");
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    //mostrar pdf en la aplicacion
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
}
