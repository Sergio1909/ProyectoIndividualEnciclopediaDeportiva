package com.example.enciclopediadeportiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.enciclopediadeportiva.Entidades.DeporteDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ListaDeportesVerano extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DeporteDto[] listaDeportes;
    private StorageReference storageReference;
    private FirebaseStorage fStorage;
    private int DETALLES_DEPORTE_VERANO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_deportes_verano);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("DEPORTE").child("VERANO").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Long longitudIncidencias = dataSnapshot.getChildrenCount();
                    int longitud = longitudIncidencias.intValue();
                    listaDeportes = new DeporteDto[longitud];
                    int contador = 0;

                    for (DataSnapshot children : dataSnapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            final DeporteDto deporte = children.getValue(DeporteDto.class);
                            final String nombreRaroDeporte = children.getKey();  deporte.setApiKey(nombreRaroDeporte);
                            //final String foto = dataSnapshot.child("foto").getValue().toString(); deporte.setFoto(foto);
                            final StorageReference fStorage = FirebaseStorage.getInstance().getReference();
                            final ListaDeportesAdapter deportesAdapter = new ListaDeportesAdapter(listaDeportes, ListaDeportesVerano.this,fStorage,DETALLES_DEPORTE_VERANO);

                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview2);
                            recyclerView.setAdapter(deportesAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ListaDeportesVerano.this));
                            listaDeportes[contador] = deporte;
                            contador++;

                        }
                    }




                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListaDeportesVerano.this,"Error Base de Datos",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbarusuario,menu);
        return true;  }
}