package com.example.enciclopediadeportiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.enciclopediadeportiva.Entidades.DeporteDto;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetallesDeporteVerano extends AppCompatActivity {



    DeporteDto deporteDto = new DeporteDto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_deporte_verano);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final String apikeyDeporte = getIntent().getStringExtra("nombreDeporte");

        databaseReference.child("DEPORTE").child("VERANO").child(apikeyDeporte).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        if (dataSnapshot1.exists()) {

                            DeporteDto deporteDto2 = dataSnapshot1.getValue(DeporteDto.class);
                            deporteDto = deporteDto2;


                            TextView nombre = findViewById(R.id.nombre_deporte); nombre.setText(deporteDto.getNombre());
                            TextView descripcion = findViewById(R.id.descripcion); descripcion.setText(deporteDto.getDescripcion());
                            publicarImagen(deporteDto.getFoto() + ".png", storageReference);
                            publicarImagen2(deporteDto.getFoto1() + ".png", storageReference);
                            publicarImagen3(deporteDto.getFoto2() + ".png", storageReference);





                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(DetallesDeporteVerano.this,"Error Base de Datos",Toast.LENGTH_LONG).show(); }
                });


    }

    // Agregar Fotografía
    public void publicarImagen (String photoName, StorageReference storageReference) {
        storageReference.child("Images").child(photoName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .load(uri)
                        .into((ImageView) findViewById(R.id.fotoDetalles)); }
        }); }
    // Agregar Fotografía1
    public void publicarImagen2 (String photoName, StorageReference storageReference) {
        storageReference.child("Images").child(photoName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .load(uri)
                        .into((ImageView) findViewById(R.id.foto1)); }
        }); }
    // Agregar Fotografía2
    public void publicarImagen3 (String photoName, StorageReference storageReference) {
        storageReference.child("Images").child(photoName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .load(uri)
                        .into((ImageView) findViewById(R.id.foto2)); }
        }); }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbarusuario,menu);
        // menu.findItem(R.id.nombreUsuario).setTitle(nombreLogueado);
        return true;  }





}



