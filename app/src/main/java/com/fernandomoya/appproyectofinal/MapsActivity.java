package com.fernandomoya.appproyectofinal;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fernandomoya.appproyectofinal.model.Perros;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String descripcion,latitud,longitud;
    private DatabaseReference mDatabase;
    private ArrayList<Marker> tmpMarker = new ArrayList<>();
    private ArrayList<Marker> realMarker = new ArrayList<>();
    Bundle infoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //Obtenga el SupportMapFragment y reciba una notificación cuando el mapa esté listo para usarse.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        mDatabase= FirebaseDatabase.getInstance().getReference();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    /**
     * Manipula el mapa una vez disponible.
     * Esta devolución de llamada se activa cuando el mapa está listo para ser utilizado.
     * Aquí es donde podemos agregar marcadores o líneas, agregar oyentes o mover la cámara. En este caso,
     * solo agregamos un marcador cerca de Sydney, Australia.
     * Si los servicios de Google Play no están instalados en el dispositivo, se le solicitará al usuario que lo instale
     * dentro del SupportMapFragment. Este método solo se activará una vez que el usuario tenga
     * instaló los servicios de Google Play y regresó a la aplicación.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mDatabase.child("perros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                infoList=getIntent().getExtras();
                descripcion=infoList.getString("descripción");
                latitud=infoList.getString("latitud");
                longitud=infoList.getString("longitud");
                Log.i("latitud", latitud) ;
                Log.i("longitud", longitud);
                for(Marker marker: realMarker){
                    marker.remove();
                }


                for(DataSnapshot snapshot:dataSnapshot.getChildren()){ //Una instancia de DataSnapshot contiene datos de una ubicación de la base de datos de Firebase.
                    //Perros mp= snapshot.getValue(Perros.class);
                    MarkerOptions markerOptions= new MarkerOptions();
                    markerOptions.position(new LatLng(Double.parseDouble(latitud), Double.parseDouble(longitud)));
                    markerOptions.title(descripcion);
                    markerOptions.snippet("Ubicación actual");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    LatLng latLng = new LatLng(markerOptions.getPosition().latitude,markerOptions.getPosition().longitude);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                    mMap.animateCamera(cameraUpdate);
                    tmpMarker.add(mMap.addMarker(markerOptions));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
