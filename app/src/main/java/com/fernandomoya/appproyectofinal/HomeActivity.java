package com.fernandomoya.appproyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fernandomoya.appproyectofinal.model.Perros;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private  int MY_PERMISSIONS_REQUEST_READ_CONTACTS ;
    private static final int PICK_IMAGE_REQUEST=1;
    ImageView imgFoto;
    ImageButton imgBtnCamera,btnGuardar,btnListar,btnGrupo;
    Uri mImageURI;
    FirebaseDatabase firebaseDatabase;
    StorageReference mStorageReference;
    DatabaseReference mDatabase;
    StorageTask mUploadImg;
    TextView lblSalir,descripcionP;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        imgFoto = findViewById(R.id.imageView3);
        imgBtnCamera = findViewById(R.id.imgBtnCamera);
        btnGuardar=findViewById(R.id.imgBtnGuardar);
        btnListar=findViewById(R.id.imgBtnListar);
        lblSalir= findViewById(R.id.lblSalir);
        btnGrupo= findViewById(R.id.imgBtnGrupoD);
        descripcionP =findViewById(R.id.txtDescripcion);
        lblSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, RegistryActivity.class);
                startActivity(intToMain);
            }
        });

        btnGuardar.setOnClickListener(this);
        btnGrupo.setOnClickListener(this);
        btnListar.setOnClickListener(this);
        imgBtnCamera.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        inicializarFirebase();

    }

    //base de datos
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        mStorageReference= FirebaseStorage.getInstance().getReference("perros");
    }
    private void limpiarCajas() {
        descripcionP.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            mImageURI = data.getData();

            Bitmap myBitmap= (Bitmap)data.getExtras().get("data");
            imgFoto.setImageBitmap(myBitmap);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }




    @Override
    public void onClick(View view) {
        descripcionP= findViewById(R.id.txtDescripcion);
        switch (view.getId()){

            //case R.id.button: Intent intent= new Intent(HomeActivity.this,MapsActivity.class);
           case R.id.imgBtnGuardar:
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
                               String descripcion=descripcionP.getText().toString();
                               Log.i("descripcionGeneral",descripcion ) ;

                               Perros p= new Perros();
                               if (location != null) {
                                   p.setDescripcion(descripcion);
                                   p.setLatitud(location.getLatitude());
                                   p.setLongitud(location.getLongitude());
                                   //p.setUrl(mImageURI.toString());
                                   mDatabase.child("perros").push().setValue(p);
                               }
                               Toast.makeText(HomeActivity.this,"Agregado",Toast.LENGTH_SHORT).show();
                           }
                       });
               //limpiarCajas();
            //startActivity(intent);

            break;


            case R.id.imgBtnGrupoD: Intent intentGrupo=  new Intent(HomeActivity.this,AboutUsActivity.class);
                startActivity(intentGrupo);
                break;

            case R.id.imgBtnListar:Intent intentListar=  new Intent(HomeActivity.this,ListActivity.class);
                startActivity(intentListar);
                break;

            case R.id.imgBtnCamera:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent,PICK_IMAGE_REQUEST);
                break;
        }
    }
}
