package com.example.enciclopediadeportiva;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.enciclopediadeportiva.Entidades.DeporteDto;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class MisDeportesFavoritosAdapter extends RecyclerView.Adapter<MisDeportesFavoritosAdapter.DeporteFavoritoViewHolder> {
    DeporteDto[] listaDeportes;
    private Context contexto;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private int condicion;
    String correoUser;


    public MisDeportesFavoritosAdapter(DeporteDto[] lista, Context contexto, StorageReference storageReference, DatabaseReference databaseReference, int condicion, String correoUser) {
        this.listaDeportes = lista;
        this.contexto = contexto;
        this.storageReference = storageReference;
        this.databaseReference = databaseReference;
        this.condicion = condicion;
        this.correoUser = correoUser;
    }

    public static class DeporteFavoritoViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreDeporte;
        public ImageView fotoDeporte;
        public Button buttonInfo2;
        public Button buttonBorrar;


        public DeporteFavoritoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreDeporte = itemView.findViewById(R.id.nombre_deporte);
            this.fotoDeporte = itemView.findViewById(R.id.foto_deporte);
            this.buttonInfo2 = itemView.findViewById(R.id.btnInfo2);
            this.buttonBorrar = itemView.findViewById(R.id.btnBorrar);
        }
    }


    @NonNull
    @Override
    public MisDeportesFavoritosAdapter.DeporteFavoritoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.deportefavorito_rv, parent, false);
        MisDeportesFavoritosAdapter.DeporteFavoritoViewHolder deporteFavoritoViewHolder = new MisDeportesFavoritosAdapter.DeporteFavoritoViewHolder(itemView);
        return deporteFavoritoViewHolder;
    }


    @Override
    public int getItemCount() {
        return listaDeportes.length;
    }

    @Override
    public void onBindViewHolder(@NonNull DeporteFavoritoViewHolder holder, int position) {
        final DeporteDto deporte = listaDeportes[position];
        final String nombreDeporte = deporte.getNombre();
        holder.nombreDeporte.setText(nombreDeporte);
        publicarImagen(deporte.getFoto() + ".png", holder);

        if (condicion == 1) {
            holder.buttonBorrar.setVisibility(View.INVISIBLE);
        }
        if (condicion == 2) {
            holder.buttonBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child("Favoritos").child(deporte.getApiKey()).removeValue();
                }
            });

        }
    }

        public void publicarImagen ( final String photoName, final DeporteFavoritoViewHolder holder){
            storageReference.child("Images").child(photoName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(contexto)
                            .load(uri)
                            .into(holder.fotoDeporte);
                }
            });
        }

    }
