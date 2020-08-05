package com.example.enciclopediadeportiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.enciclopediadeportiva.Entidades.DeporteDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MisDeportesFavoritos extends AppCompatActivity {
//Aqui se implemetaria la seccion de los deportes favoritos por usuarios
    DeporteDto[] listaMisDeportesFavoritos;
    int x = 0; int CONDICION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_deportes_favoritos);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String correoUsuario = user.getEmail();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final StorageReference fStorage = FirebaseStorage.getInstance().getReference();

        databaseReference.child("favoritos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Long longitud = dataSnapshot.getChildrenCount();
                    int longitudWL = longitud.intValue();
                    ArrayList listaDeportesFavoritos = new ArrayList<DeporteDto>();

                    for(DataSnapshot children: dataSnapshot.getChildren()){
                        if (dataSnapshot.exists()){
                            final DeporteDto deporteDto = children.getValue(DeporteDto.class);
                            String nombreRaro = children.getKey(); deporteDto.setApiKey(nombreRaro);
                            if (deporteDto.getVysor().equals(correoUsuario) ) { listaDeportesFavoritos.add(deporteDto);}
                        }
                    }
                    listaMisDeportesFavoritos = new DeporteDto[longitudWL];

                    for (int x=0; x<longitudWL; x++){
                        listaMisDeportesFavoritos[x] = (DeporteDto) listaDeportesFavoritos.get(x); }


                    MisDeportesFavoritosAdapter misdeportesFavoritos = new MisDeportesFavoritosAdapter(listaMisDeportesFavoritos, MisDeportesFavoritos.this,fStorage,databaseReference,CONDICION,
                            correoUsuario);
                    RecyclerView recyclerView = findViewById(R.id.RecyclerViewMisDeportesFavoritos);
                    recyclerView.setAdapter(misdeportesFavoritos);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MisDeportesFavoritos.this));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }



}