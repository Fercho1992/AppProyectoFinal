package com.fernandomoya.appproyectofinal;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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

public class ListActivity extends AppCompatActivity {

    private RecyclerView rw;
    List<Perros> listaPerros;

    ListView myListView;
    ArrayList<String> myArrayList= new ArrayList<>();
    Apapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        rw= findViewById(R.id.recycleV);
        rw.setLayoutManager(new LinearLayoutManager(this));
        listaPerros= new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("perros");
        adapter= new Apapter(listaPerros);
        rw.setAdapter(adapter);
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
}
