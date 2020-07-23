package com.fernandomoya.appproyectofinal;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fernandomoya.appproyectofinal.model.Perros;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity implements ItemClickListener{

    private RecyclerView rw;
    private List<Perros> listaPerros;
    private Apapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("perros");
        listaPerros= new ArrayList<>();
        rw= findViewById(R.id.recycleV);
        rw.setLayoutManager(new LinearLayoutManager(this));
        rw.setItemAnimator(new DefaultItemAnimator());

        adapter= new Apapter(listaPerros,R.layout.row_recycler,this);
        rw.setAdapter(adapter);
        adapter.setClickListener(this);
        database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPerros.removeAll(listaPerros);
                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    for(DataSnapshot dSnapshot : ds.getChildren()){
                        Perros perros= dSnapshot .getValue(Perros.class);
                        Log.d("TAG", perros.getDescripcion());
                        listaPerros.add(perros);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onClick(View view, int position) {
        Perros perros=listaPerros.get(position);
        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra("descripción", perros.getDescripcion());
        i.putExtra("latitud", perros.getLatitud().toString());
        i.putExtra("longitud", perros.getLongitud().toString());
        Log.i("hello", perros.getDescripcion());
        Log.i("latitud", perros.getLatitud().toString()) ;
        Log.i("longitud", perros.getLongitud().toString());
        startActivity(i);
    }
}
