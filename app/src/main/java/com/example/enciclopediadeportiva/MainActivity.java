package com.example.enciclopediadeportiva;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;


import com.example.enciclopediadeportiva.Entidades.UsuarioDto;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {


    DatabaseReference databaseReference;
    String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }

    public void iniciarSesion(View view){

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Log.d("infoApp","inicio de sesion exitoso");

                //Detectar rol
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                DatabaseReference referencia2 = databaseReference.child("Usuarios").child(uid);

                if (rol == null){
                    Log.d("infoApp","Esperando datos");
                    referencia2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                Log.d("infoApp","existe");
                                UsuarioDto usuario = snapshot.getValue(UsuarioDto.class);
                                rol = usuario.getRol();
                                Log.d("infoApp","rol:" + rol);
                                if (rol.equals("UsuarioED")){
                                    Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                                    startActivity(intent);


                                } else {
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);

                                }
                            }else{
                                Log.d("infoApp","no existes");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }






            } else {

                Log.d("infoApp","inicio erroneo");
            }

        }

    }

    public void abrirRegistrosActivity(View view){

        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }


}
