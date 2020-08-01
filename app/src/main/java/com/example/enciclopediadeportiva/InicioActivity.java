package com.example.enciclopediadeportiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbarusuario,menu);
        return true;  }

    public boolean onCreateOptionsMenu(@NonNull MenuItem item) {
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