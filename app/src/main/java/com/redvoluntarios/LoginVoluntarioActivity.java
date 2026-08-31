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

public class LoginVoluntarioActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnIngresar;
    private TextView txtVolverAlMain;
    private TextView txtIrARegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_voluntario);

        // Vinculación segura de componentes (R)
        edtEmail = findViewById(R.id.edtLoginEmail);
        edtPassword = findViewById(R.id.edtLoginPassword);
        btnIngresar = findViewById(R.id.btnLoginIngresar);
        txtIrARegistro = findViewById(R.id.txtIrARegistro);
        txtVolverAlMain = findViewById(R.id.txtVolverAlMain);

        // Escuchador para ingresar
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                // Validación lógica básica
                if (email.isEmpty()) {
                    edtEmail.setError("Ingrese su correo");
                    return;
                }
                if (pass.isEmpty()) {
                    edtPassword.setError("Ingrese su contraseña");
                    return;
                }

                // Simulación del Login (A realizar con SQLite en la Unidad II)
                if (email.equals("voluntario@gmail.com") && pass.equals("123456")) {
                    Toast.makeText(LoginVoluntarioActivity.this, "¡Ingreso exitoso!", Toast.LENGTH_SHORT).show();
                    // Aquí se lanzará la pantalla de mapa de ayuda
                } else {
                    Toast.makeText(LoginVoluntarioActivity.this, "Credenciales incorrectas de prueba", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Escuchador para volver al main
        txtVolverAlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Crear el Intent de redirección explícito
                Intent intent = new Intent(LoginVoluntarioActivity.this, MainActivity.class);

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
        // Intent para abrir el Registro
        txtIrARegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginVoluntarioActivity.this, RegistroVoluntarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}