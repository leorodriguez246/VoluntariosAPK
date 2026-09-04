package com.redvoluntarios.vistas.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.redvoluntarios.R;
import com.redvoluntarios.vistas.principal.MainActivity;

/**
 * PANTALLA: RegistroVoluntarioActivity
 * ============================================================================
 * Formulario de creación de cuenta para nuevos voluntarios.
 * Demuestra el uso de validaciones de texto y selección obligatoria de CheckBox.
 */
public class RegistroVoluntarioActivity extends AppCompatActivity {

    private EditText edtNombre, edtEmail, edtTelefono, edtPassword;
    private CheckBox chkTerminos;
    private Button btnRegistrar;
    private TextView txtVolverLogin;
    private TextView txtVolverAlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_voluntario);

        // Vinculación de vistas del formulario
        edtNombre = findViewById(R.id.edtRegNombre);
        edtEmail = findViewById(R.id.edtRegEmail);
        edtTelefono = findViewById(R.id.edtRegTelefono);
        edtPassword = findViewById(R.id.edtRegPassword);
        chkTerminos = findViewById(R.id.chkTerminos);
        btnRegistrar = findViewById(R.id.btnRegRegistrar);
        txtVolverLogin = findViewById(R.id.txtVolverAlLogin);
        txtVolverAlMain = findViewById(R.id.txtVolverAlMain);

        // Botón Registrar Cuenta
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombre.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String fono = edtTelefono.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                // Validaciones de contenido obligatorio
                if (nombre.isEmpty()) { edtNombre.setError("Nombre obligatorio"); return; }
                if (email.isEmpty()) { edtEmail.setError("Correo obligatorio"); return; }
                if (fono.isEmpty()) { edtTelefono.setError("Teléfono obligatorio"); return; }
                if (pass.isEmpty() || pass.length() < 6) {
                    edtPassword.setError("Contraseña mínima de 6 caracteres");
                    return;
                }

                // Validación de casilla de verificación (CheckBox)
                if (!chkTerminos.isChecked()) {
                    Toast.makeText(RegistroVoluntarioActivity.this,
                            "Debes aceptar los términos y condiciones de voluntariado",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(RegistroVoluntarioActivity.this, "¡Registro exitoso! Ya puedes iniciar sesión.", Toast.LENGTH_LONG).show();
                finish(); // Regresa a la pantalla anterior (Login)
            }
        });

        // Botón volver al inicio
        txtVolverAlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroVoluntarioActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // Retornar al Login
        txtVolverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}