package com.fernandomoya.appproyectofinal;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fernandomoya.appproyectofinal.model.Perros;

import java.util.List;

public class Apapter extends RecyclerView.Adapter<Apapter.PerroViewHolder> {
    List<Perros> listPerros;

    public Apapter(List<Perros> listPerros) {
        this.listPerros = listPerros;
    }

    @Override
    public PerroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler,parent,false);
       PerroViewHolder holder=new PerroViewHolder(v);
       return holder;
    }

    @Override
    public void onBindViewHolder(PerroViewHolder holder, int position) {
        Perros perros=listPerros.get(position);
        holder.lstRvDescripcion.setText(perros.getDescripcion());
        holder.lstRvLatitud.setText(perros.getLatitud().toString());
        holder.lstRvLongitud.setText(perros.getLongitud().toString());
    }

    @Override
    public int getItemCount() {
        return listPerros.size();
    }

    public static class PerroViewHolder extends RecyclerView.ViewHolder{
        TextView lstRvDescripcion, lstRvLatitud,lstRvLongitud;
        public PerroViewHolder(View itemView) {
            super(itemView);
            lstRvDescripcion=itemView.findViewById(R.id.lstDescripcion);
            lstRvLatitud=itemView.findViewById(R.id.lstLatitud);
            lstRvLongitud=itemView.findViewById(R.id.lstLongitud);
        }
    }
}
