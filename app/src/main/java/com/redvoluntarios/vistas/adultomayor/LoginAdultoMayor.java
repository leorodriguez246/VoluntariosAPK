package com.redvoluntarios.vistas.adultomayor;

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
 * PANTALLA: LoginAdultoMayor
 * ============================================================================
 * Gestiona el acceso simplificado para adultos mayores mediante RUT/Teléfono y Contraseña.
 * Redirige hacia la pantalla de creación de solicitudes de ayuda.
 */
public class LoginAdultoMayor extends AppCompatActivity {

    private EditText edtRut, edtPassword;
    private Button btnIngresar;
    private TextView txtIrARegistro;
    private TextView txtVolverAlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_adulto_mayor);

        // Vinculación de controles de la interfaz
        edtRut = findViewById(R.id.edtLoginAmRut);
        edtPassword = findViewById(R.id.edtLoginAmPassword);
        btnIngresar = findViewById(R.id.btnLoginAmIngresar);
        txtIrARegistro = findViewById(R.id.txtIrARegistroAm);
        txtVolverAlMain = findViewById(R.id.txtVolverAlMainAm);

        // Evento de ingreso
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rut = edtRut.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                // Validaciones de ingreso
                if (rut.isEmpty()) {
                    edtRut.setError("Ingrese su RUT o Teléfono");
                    return;
                }
                if (pass.isEmpty()) {
                    edtPassword.setError("Ingrese su contraseña");
                    return;
                }

                Toast.makeText(LoginAdultoMayor.this, "¡Bienvenido! Abriendo solicitud de ayuda...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginAdultoMayor.this, CrearSolicitudActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Evento para navegar al registro de Adultos Mayores
        txtIrARegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAdultoMayor.this, RegistroAdultosActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        // Evento para volver al Inicio principal
        txtVolverAlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAdultoMayor.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}