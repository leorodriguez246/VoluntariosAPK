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

/**
 * PANTALLA PRINCIPAL: MainActivity
 * ============================================================================
 * Punto de entrada inicial de la aplicación Red Cuidar.
 * Muestra las opciones principales de navegación según el rol seleccionado por el usuario:
 *  1. Adulto Mayor ("Necesito Ayuda")
 *  2. Voluntario ("Quiero ser Voluntario")
 */
public class MainActivity extends AppCompatActivity {

    private Button btnRolMayor;
    private Button btnRolVoluntario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Configurar tema claro por defecto
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        // Instanciar la interfaz gráfica XML de la pantalla principal
        setContentView(R.layout.activity_main);

        // Vinculación de botones
        btnRolMayor = findViewById(R.id.btnRolMayor);
        btnRolVoluntario = findViewById(R.id.btnRolVoluntario);

        // Escuchador de eventos: Perfil Adulto Mayor
        btnRolMayor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent explícito hacia el login de Adulto Mayor
                Intent intent = new Intent(MainActivity.this, LoginAdultoMayor.class);
                startActivity(intent);
            }
        });

        // Escuchador de eventos: Perfil Voluntario
        btnRolVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent explícito hacia el login de Voluntarios
                Intent intent = new Intent(MainActivity.this, LoginVoluntarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}