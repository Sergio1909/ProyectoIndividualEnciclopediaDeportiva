package com.example.enciclopediadeportiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enciclopediadeportiva.Entidades.UsuarioDto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    private EditText editTextMail, editTextPass;
    DatabaseReference databaseReference;
    String rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginActivity.this);
        editTextMail = findViewById(R.id.editTextTextEmailAddress);
        editTextPass = findViewById(R.id.editTextTextPassword);
        databaseReference = FirebaseDatabase.getInstance().getReference();



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void signIn(View view) {
        final String email = editTextMail.getText().toString().trim();
        final String password = editTextPass.getText().toString().trim();
        Log.d("InfoApp", "signIn:" + email);

        progressDialog.setTitle("Accediendo a la aplicación...");
        progressDialog.show();

        // Comienza código de logueo con correo y password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("InfoApp", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            DatabaseReference referencia2 = databaseReference.child("Users").child(uid);
                            boolean emailVerified = user.isEmailVerified();
                            if (emailVerified) {

                                if (rol == null) {
                                    Log.d("infoApp", "Esperando datos");
                                    referencia2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                Log.d("infoApp", "existe");
                                                UsuarioDto usuario = snapshot.getValue(UsuarioDto.class);
                                                rol = usuario.getRol();
                                                Log.d("infoApp", "rol:" + rol);
                                                if (rol.equals("UsuarioED")) {
                                                    Intent intent = new Intent(LoginActivity.this, InicioActivity.class);

                                                    startActivity(intent);


                                                } else {
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                                    startActivity(intent);

                                            }
                                            } else {
                                                Log.d("infoApp", "no existes");
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            } else {

                                Toast.makeText(LoginActivity.this,"La cuenta no se encuentra verificada",Toast.LENGTH_LONG).show();
                            }

                        } else {
                            // Si falla el logueo mostrar este error
                            Log.w("InfoApp", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos",
                                    Toast.LENGTH_SHORT).show();
                        }


                        progressDialog.dismiss();

                    }
                });
        // [END sign_in_with_email]
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //codigo adicional
        this.finish();
    }



}