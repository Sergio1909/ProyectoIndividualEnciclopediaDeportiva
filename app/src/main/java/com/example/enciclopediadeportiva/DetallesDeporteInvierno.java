package com.example.enciclopediadeportiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.enciclopediadeportiva.Entidades.DeporteDto;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class DetallesDeporteInvierno extends AppCompatActivity {




    DeporteDto deporteDto = new DeporteDto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_deporte_invierno);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final String apikeyDeporte = getIntent().getStringExtra("nombreDeporte");

        databaseReference.child("DEPORTE").child("INVIERNO").child(apikeyDeporte).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        if (dataSnapshot1.exists()) {

                            DeporteDto deporteDto2 = dataSnapshot1.getValue(DeporteDto.class);
                            deporteDto = deporteDto2;


                            TextView nombre = findViewById(R.id.nombre_deporte_invierno); nombre.setText(deporteDto.getNombre());
                            TextView descripcion = findViewById(R.id.descripcionInvierno); descripcion.setText(deporteDto.getDescripcion());
                            descripcion.setMovementMethod(new ScrollingMovementMethod());
                            publicarImagen(deporteDto.getFoto() + ".png", storageReference);
                            publicarImagen2(deporteDto.getFoto1() + ".png", storageReference);
                            publicarImagen3(deporteDto.getFoto2() + ".png", storageReference);





                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(DetallesDeporteInvierno.this,"Error Base de Datos",Toast.LENGTH_LONG).show(); }
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
                        .into((ImageView) findViewById(R.id.fotoDetallesInvierno)); }
        }); }
    // Agregar Fotografía1
    public void publicarImagen2 (String photoName, StorageReference storageReference) {
        storageReference.child("Images").child(photoName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .load(uri)
                        .into((ImageView) findViewById(R.id.foto1Invierno)); }
        }); }
    // Agregar Fotografía2
    public void publicarImagen3 (String photoName, StorageReference storageReference) {
        storageReference.child("Images").child(photoName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .load(uri)
                        .into((ImageView) findViewById(R.id.foto2Invierno)); }
        }); }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbarusuario,menu);
        return true;  }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(DetallesDeporteInvierno.this, MainActivity.class));
                return true;
            case R.id.winter:
                startActivity(new Intent(DetallesDeporteInvierno.this, ListaDeportesInvierno.class));
                return true;
            case R.id.summer:
                startActivity(new Intent(DetallesDeporteInvierno.this, ListaDeportesVerano.class));
                return true;

            case R.id.olympics:
                startActivity(new Intent(DetallesDeporteInvierno.this, InicioActivity.class));
                return true;


        }
        return onOptionsItemSelected(item);
    }




}



