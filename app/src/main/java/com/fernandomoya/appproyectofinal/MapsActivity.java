package com.fernandomoya.appproyectofinal;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.fernandomoya.appproyectofinal.model.Perros;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
    private DatabaseReference mDatabase;
    private ArrayList<Marker> tmpMarker = new ArrayList<>();
    private ArrayList<Marker> realMarker = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

                for(Marker marker: realMarker){
                    marker.remove();
                }

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    //Perros mp= snapshot.getValue(Perros.class);
                    Double latitud= -0.458331;
                    Log.i("latitud", latitud.toString()) ;
                    Double longitud=-78.5605468;
                    Log.i("longitud", longitud.toString());
                    MarkerOptions markerOptions= new MarkerOptions();
                    markerOptions.position(new LatLng(latitud, longitud));
                    tmpMarker.add(mMap.addMarker(markerOptions));
                }

                realMarker.clear();
                realMarker.addAll(tmpMarker);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
