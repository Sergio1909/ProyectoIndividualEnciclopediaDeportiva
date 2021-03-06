package com.example.enciclopediadeportiva;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.enciclopediadeportiva.Entidades.UsuarioDto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    private EditText editTextMail, editTextPass, editTextName;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    UsuarioDto usuario = new UsuarioDto();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro);
        editTextMail = findViewById(R.id.editTextTextPersonName);
        editTextPass = findViewById(R.id.editTextTextPersonName2);
        editTextName = findViewById(R.id.editTextTextPersonName3);
        progressDialog = new ProgressDialog(RegistroActivity.this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            //Verificar si usuario ya esta logueado

        }
    }

    public void registrarUsuario(View view){
        final String correo = editTextMail.getText().toString().trim();
        final String contrasena = editTextPass.getText().toString().trim();
        final String nombre = editTextName.getText().toString().trim();
        final String rol = "UsuarioED";
        progressDialog.setTitle("Registrando...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    boolean emailVerified = user.isEmailVerified();
                    if (!emailVerified) {

                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Adicion de campos extra
                                    Log.d("InfoApp","Se logró enviar el correo");
                                    usuario = new UsuarioDto(
                                            nombre,
                                            correo,
                                            rol
                                    );
                                    Log.d("InfoApp","A punto de iniciar el listener de DB...");
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegistroActivity.this, "Registrado exitosamente, verifique su correo", Toast.LENGTH_LONG).show();
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(RegistroActivity.this,MainActivity.class);

                                                startActivity(intent);

                                            } else {
                                                //Mostrar error

                                            }
                                        }

                                    });

                                } else {
                                    Toast.makeText(RegistroActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                }

                            }
                        });
                    }








                }else{

                    Toast.makeText(RegistroActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }

        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //codigo adicional
        this.finish();
    }
}