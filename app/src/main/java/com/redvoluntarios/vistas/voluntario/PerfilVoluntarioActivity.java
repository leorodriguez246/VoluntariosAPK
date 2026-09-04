package com.redvoluntarios.vistas.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.redvoluntarios.R;
import com.redvoluntarios.vistas.principal.MainActivity;

public class PerfilVoluntarioActivity extends AppCompatActivity {

    private EditText edtNombre, edtEmail, edtTelefono, edtComuna;
    private Button btnCambiarFoto, btnGuardar, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_voluntario);

        edtNombre = findViewById(R.id.edtPerfilNombre);
        edtEmail = findViewById(R.id.edtPerfilEmail);
        edtTelefono = findViewById(R.id.edtPerfilTelefono);
        edtComuna = findViewById(R.id.edtPerfilComuna);
        btnCambiarFoto = findViewById(R.id.btnCambiarFoto);
        btnGuardar = findViewById(R.id.btnPerfilGuardar);
        btnCerrarSesion = findViewById(R.id.btnPerfilCerrarSesion);

        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilVoluntarioActivity.this,
                        "Seleccionar imagen de la galería...",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombre.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();

                if (nombre.isEmpty()) {
                    edtNombre.setError("El nombre no puede estar vacío");
                    return;
                }
                if (email.isEmpty()) {
                    edtEmail.setError("El correo no puede estar vacío");
                    return;
                }

                Toast.makeText(PerfilVoluntarioActivity.this,
                        "¡Perfil actualizado con éxito!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilVoluntarioActivity.this,
                        "Has cerrado sesión correctamente",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PerfilVoluntarioActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}