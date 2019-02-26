package com.example.beto.movil_practica;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter_Client extends RecyclerView.Adapter<RecyclerViewAdapter_Client.ViewHolder>{

    private ArrayList<String> names;
    private ArrayList<String> debs;
    private ArrayList<String> ruts;
    private Context context;

    public RecyclerViewAdapter_Client(ArrayList<String> names,ArrayList<String> debs,ArrayList<String> ruts, Context context) {
        this.names = names;
        this.debs = debs;
        this.ruts = ruts;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.client_list_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textItem.setText(names.get(i));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textItem;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textItem = itemView.findViewById(R.id.textView_client_name);
            constraintLayout = itemView.findViewById(R.id.parent_list_client);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            Intent intent = new Intent(v.getContext(),VentasActivity.class);
            int itemPosition = getAdapterPosition();
            String nombre = names.get(itemPosition);
            String deuda = debs.get(itemPosition);
            String rut = ruts.get(itemPosition);
            intent.putExtra("nombre",nombre);
            intent.putExtra("deuda",deuda);
            intent.putExtra("rut",rut);
            context.startActivity(intent);
        }
    }
}
