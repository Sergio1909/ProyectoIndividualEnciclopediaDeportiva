package com.example.enciclopediadeportiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioActivity extends AppCompatActivity {
    private ImageView summerView;
    private ImageView winterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        summerView = (ImageView) findViewById(R.id.summerview);
        winterView = (ImageView) findViewById(R.id.winterview);

        summerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InicioActivity.this, ListaDeportesVerano.class);
                startActivity(intent);

            }
        });

        winterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, ListaDeportesInvierno.class);
                startActivity(intent);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbarusuario,menu);
        return true;  }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(InicioActivity.this, MainActivity.class));
                return true;
            case R.id.winter:
                startActivity(new Intent(InicioActivity.this, ListaDeportesInvierno.class));
                return true;
            case R.id.summer:
                startActivity(new Intent(InicioActivity.this, ListaDeportesVerano.class));
                return true;

                case R.id.olympics:
                startActivity(new Intent(InicioActivity.this, InicioActivity.class));
                return true;

        }
        return onOptionsItemSelected(item);
    }
}