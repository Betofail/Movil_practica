package com.example.beto.movil_practica;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;

public class RecyclerViewAdapter_Product extends RecyclerView.Adapter<RecyclerViewAdapter_Product.ViewHolder> {
    private ArrayList<String> codigo = new ArrayList<>();
    private ArrayList<String> producto = new ArrayList<>();
    private ArrayList<String> stock = new ArrayList<>();
    private ArrayList<String> precio = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter_Product(Context context,ArrayList<String> codigo, ArrayList<String> producto, ArrayList<String> stock, ArrayList<String> precio) {
        this.codigo = codigo;
        this.producto = producto;
        this.stock = stock;
        this.precio = precio;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.codigo.setText(codigo.get(i));
        viewHolder.producto.setText(producto.get(i));
        viewHolder.stock.setText(stock.get(i));
        viewHolder.precio.setText(precio.get(i));
    }

    @Override
    public int getItemCount() {
        return codigo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView codigo;
        TextView producto;
        TextView stock;
        TextView precio;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.producto_codigo);
            producto = itemView.findViewById(R.id.producto_nombre);
            stock = itemView.findViewById(R.id.producto_stock);
            precio = itemView.findViewById(R.id.producto_precio);
            constraintLayout = itemView.findViewById(R.id.producto_activity);
        }
    }
}
