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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class ListaDeportesAdapter extends RecyclerView.Adapter<ListaDeportesAdapter.DeporteViewHolder> {
    DeporteDto[] listaDeportes;
    private Context contexto;
    private StorageReference storageReference;


    public ListaDeportesAdapter(DeporteDto[] lista, Context contexto,StorageReference storageReference) {
        this.listaDeportes = lista;
        this.contexto = contexto;
        this.storageReference=storageReference;
    }

    public static class DeporteViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreDeporte;
        public ImageView fotoDeporte;


        public DeporteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreDeporte = itemView.findViewById(R.id.nombre_deporte);
            this.fotoDeporte = itemView.findViewById(R.id.foto_deporte); }}


    @NonNull
    @Override
    public DeporteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.deporte_rv, parent, false);
        DeporteViewHolder deporteViewHolder = new DeporteViewHolder(itemView);
        return deporteViewHolder;
    }

    @Override
    public void onBindViewHolder(DeporteViewHolder holder, int position) {
        final DeporteDto deporte = listaDeportes[position];
        final String nombreDeporte = deporte.getNombre();holder.nombreDeporte.setText(nombreDeporte);
        publicarImagen(deporte.getFoto() + ".png", holder);



    }
    public void publicarImagen (final String photoName, final DeporteViewHolder holder){
        storageReference.child("Images").child(photoName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(contexto)
                        .load(uri)
                        .into(holder.fotoDeporte); }
        }); }

    @Override
    public int getItemCount() {
        return listaDeportes.length;
    }

}
