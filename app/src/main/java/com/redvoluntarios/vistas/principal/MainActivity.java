package com.redvoluntarios.vistas.principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.redvoluntarios.R;
import com.redvoluntarios.vistas.adultomayor.LoginAdultoMayor;
import com.redvoluntarios.vistas.voluntario.LoginVoluntarioActivity;

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
                Intent intent = new Intent(MainActivity.this, LoginAdultoMayor.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        btnRolVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginVoluntarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}