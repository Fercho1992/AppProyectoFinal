package com.fernandomoya.appproyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fernandomoya.appproyectofinal.model.Perros;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private  int MY_PERMISSIONS_REQUEST_READ_CONTACTS ;
    Button btnLogout, btnMaps;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    EditText descripcionP;
    Perros perros;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        btnLogout = findViewById(R.id.logout);
        descripcionP= findViewById(R.id.editText3);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, RegistryActivity.class);
                startActivity(intToMain);
            }
        });

        btnMaps=findViewById(R.id.button);
        btnMaps.setOnClickListener(this);
        inicializarFirebase();
    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
    }
    private void limpiarCajas() {
        descripcionP.setText("");
    }


    @Override
    public void onClick(View view) {



        switch (view.getId()){

            //case R.id.button: Intent intent= new Intent(HomeActivity.this,MapsActivity.class);
           case R.id.button:
               if(ActivityCompat.checkSelfPermission(this,
                       Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                       && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                   ActivityCompat.requestPermissions(HomeActivity.this,
                           new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                           MY_PERMISSIONS_REQUEST_READ_CONTACTS);
               }
               fusedLocationClient.getLastLocation()
                       .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                           @Override
                           public void onSuccess(Location location) {
                               String descripcionGeneral =descripcionP.getText().toString();
                               if (location != null) {
                                   Perros p= new Perros();
                                   p.setDescripcion(descripcionGeneral);
                                   p.setLatitud(String.valueOf(location.getLatitude()));
                                   p.setLongitud(String.valueOf(location.getLongitude()));
                                   Log.e("Latitud: ",+location.getLatitude()+" Longitud: "+ location.getLongitude()+" Descripcion: "+p.getDescripcion());
                                   mDatabase.child("perros").push().setValue(p);
                                   Toast.makeText(HomeActivity.this,"Agregado",Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
               limpiarCajas();
            //startActivity(intent);

            break;
        }
    }
}
