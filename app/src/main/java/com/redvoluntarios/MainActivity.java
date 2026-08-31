package com.redvoluntarios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private Button btnRolMayor;
    private Button btnRolVoluntario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnRolMayor = findViewById(R.id.btnRolMayor);
        btnRolVoluntario = findViewById(R.id.btnRolVoluntario);

        btnRolMayor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mensaje emergente temporal de confirmación
                Toast.makeText(MainActivity.this,
                        "Perfil seleccionado: Adulto Mayor. Cargando solicitudes...",
                        Toast.LENGTH_SHORT).show();

            }
        });

        btnRolVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creamos el Intent explícito para ir de MainActivity a LoginVoluntarioActivity
                Intent intent = new Intent(MainActivity.this, LoginVoluntarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}