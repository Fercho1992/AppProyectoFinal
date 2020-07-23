package com.fernandomoya.appproyectofinal;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;// es una versión más avanzada y flexible de ListView 
import com.fernandomoya.appproyectofinal.model.Perros;
import java.util.List;

public class Apapter extends RecyclerView.Adapter<Apapter.PerroViewHolder> {
    private  List<Perros> listPerros;
    private int rowLayout;
    private Context mContext;
    private ItemClickListener clickListener;


    public Apapter(List<Perros> listPerros, int rowLayout, Context mContext) {
        this.listPerros = listPerros;
        this.rowLayout = rowLayout;
        this.mContext = mContext;
    }

    @Override
    public PerroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
       View v= layoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
       PerroViewHolder holder=new PerroViewHolder(v);
       return holder;
    }

    @Override
    public void onBindViewHolder(PerroViewHolder holder, int position) {
        Perros perros=listPerros.get(position);
        holder.lstRvDescripcion.setText("Descripción: "+perros.getDescripcion());
        holder.lstRvLatitud.setText("Latitud: "+perros.getLatitud().toString());
        holder.lstRvLongitud.setText("Longitud: "+perros.getLongitud().toString());


    }

    @Override
    public int getItemCount() {
        return listPerros.size();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public  class PerroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout parentLayout;
        TextView lstRvDescripcion, lstRvLatitud,lstRvLongitud;

        public PerroViewHolder(View itemView) {
            super(itemView);
            lstRvDescripcion=itemView.findViewById(R.id.lstDescripcion);
            lstRvLatitud=itemView.findViewById(R.id.lstLatitud);
            lstRvLongitud=itemView.findViewById(R.id.lstLongitud);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
        }
    }


