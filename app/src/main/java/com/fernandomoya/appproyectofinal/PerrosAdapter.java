package com.fernandomoya.appproyectofinal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fernandomoya.appproyectofinal.model.Perros;


public class PerrosAdapter  {

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtDescripcion;
        TextView txtLatitud;
        TextView txtLongitud;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //txtDescripcion= itemView.findViewById(R.id.lblDescripcion);

        }
    }

}
