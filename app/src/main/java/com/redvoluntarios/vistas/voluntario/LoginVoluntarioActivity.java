package com.redvoluntarios.vistas.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.redvoluntarios.R;
import com.redvoluntarios.vistas.principal.MainActivity;

/**
 * PANTALLA: LoginVoluntarioActivity
 * ============================================================================
 * Maneja la autenticación e ingreso de los voluntarios registrados.
 * Realiza validaciones de entrada en los campos de texto e Inicia el Hub de Voluntarios.
 */
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

        // Vinculación de vistas
        edtEmail = findViewById(R.id.edtLoginEmail);
        edtPassword = findViewById(R.id.edtLoginPassword);
        btnIngresar = findViewById(R.id.btnLoginIngresar);
        txtIrARegistro = findViewById(R.id.txtIrARegistro);
        txtVolverAlMain = findViewById(R.id.txtVolverAlMain);

        // Botón Iniciar Sesión con validaciones de campos
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                // Validaciones de ingreso
                if (email.isEmpty()) {
                    edtEmail.setError("Ingrese su correo");
                    return;
                }
                if (pass.isEmpty()) {
                    edtPassword.setError("Ingrese su contraseña");
                    return;
                }

                // Autenticación de prueba (A conectar con SQLite / Firebase)
                if (email.equals("voluntario@gmail.com") && pass.equals("123456")) {
                    Toast.makeText(LoginVoluntarioActivity.this, "¡Ingreso exitoso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginVoluntarioActivity.this, HubVoluntarioActivity.class);
                    startActivity(intent);
                    finish(); // Cerrar el login para no volver atrás con el botón físico
                } else {
                    Toast.makeText(LoginVoluntarioActivity.this,
                            "Credenciales incorrectas de prueba (usar voluntario@gmail.com / 123456)",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // Redirección a la pantalla de Inicio
        txtVolverAlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginVoluntarioActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Redirección al formulario de Registro
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