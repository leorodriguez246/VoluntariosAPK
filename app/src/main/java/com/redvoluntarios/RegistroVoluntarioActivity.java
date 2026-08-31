package com.redvoluntarios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class RegistroVoluntarioActivity extends AppCompatActivity {

    private EditText edtNombre, edtEmail, edtTelefono, edtPassword;
    private Button btnRegistrar;
    private TextView txtVolverLogin;
    private TextView txtVolverAlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_voluntario);

        edtNombre = findViewById(R.id.edtRegNombre);
        edtEmail = findViewById(R.id.edtRegEmail);
        edtTelefono = findViewById(R.id.edtRegTelefono);
        edtPassword = findViewById(R.id.edtRegPassword);
        btnRegistrar = findViewById(R.id.btnRegRegistrar);
        txtVolverLogin = findViewById(R.id.txtVolverAlLogin);
        txtVolverAlMain = findViewById(R.id.txtVolverAlMain);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombre.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String fono = edtTelefono.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                // Validaciones de robustez de campos
                if (nombre.isEmpty()) { edtNombre.setError("Nombre obligatorio"); return; }
                if (email.isEmpty()) { edtEmail.setError("Correo obligatorio"); return; }
                if (fono.isEmpty()) { edtTelefono.setError("Teléfono obligatorio"); return; }
                if (pass.isEmpty() || pass.length() < 6) {
                    edtPassword.setError("Contraseña mínima de 6 caracteres");
                    return;
                }

                // Mensaje de éxito formativo
                Toast.makeText(RegistroVoluntarioActivity.this, "¡Registro exitoso! Ya puedes iniciar sesión.", Toast.LENGTH_LONG).show();

                // Finaliza esta pantalla para retornar a la anterior (Login) de forma natural
                finish();
            }
        });

        // Escuchador para volver al main
        txtVolverAlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Crear el Intent de redirección explícito
                Intent intent = new Intent(RegistroVoluntarioActivity.this, MainActivity.class);

                // 2. Aplicar banderas para limpiar la pila de pantallas intermedias (como el Login)
                // Esto busca una instancia existente de MainActivity en el fondo, destruye todo lo que
                // esté encima de ella (el Login y el Registro) y la trae de vuelta al primer plano.
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // 3. Iniciar el viaje de navegación
                startActivity(intent);

                // 4. Finalizar la pantalla de registro actual para liberarla de la memoria
                finish();
            }
        });


        // Retornar al login de forma manual si ya tiene cuenta
        txtVolverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}