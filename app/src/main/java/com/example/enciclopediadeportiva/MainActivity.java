package com.example.enciclopediadeportiva;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.setLanguageCode("es-419");
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser != null){
            Log.d("infoApp","Usuario logueado");
            String email = currentUser.getEmail();
            String uid = currentUser.getUid();
            String displayName = currentUser.getDisplayName();
            boolean emailVerified = currentUser.isEmailVerified();

            Log.d("infoApp","email: " + email);
            Log.d("infoApp","uid: " + uid);
            Log.d("infoApp","displayName: " + displayName);
            Log.d("infoApp","emailVerified: " + emailVerified);

            boolean v = true;
            if(emailVerified == v){
                Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
            }

        }else{
            Log.d("infoApp","Usuario no logueado");
        }

    }

    public void iniciarSesion(View view){

        List<AuthUI.IdpConfig> listaProveedores =
                Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()

                );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(listaProveedores)
                        .build(),
                1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Log.d("infoApp","inicio de sesión exitoso!");
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.setLanguageCode("es-419");
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if(!currentUser.isEmailVerified()){
                    currentUser.sendEmailVerification()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this,
                                            "Se le ha enviado un correo para validar su cuenta",
                                            Toast.LENGTH_SHORT).show();
                                    Log.d("infoApp","Envío de correo exitoso");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("infoApp","error al enviar el correo");
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



}
