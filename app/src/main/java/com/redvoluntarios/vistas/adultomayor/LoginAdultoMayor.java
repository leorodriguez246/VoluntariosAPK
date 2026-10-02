package com.redvoluntarios.vistas.adultomayor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.redvoluntarios.R;
import com.redvoluntarios.controladores.UsuarioController;
import com.redvoluntarios.modelos.Usuario;
import com.redvoluntarios.vistas.principal.MainActivity;
import com.redvoluntarios.vistas.principal.SesionManager;

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
    private SesionManager sesionManager;
    private UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_adulto_mayor);
        
        // Instanciar el gestor de sesiones y el controlador
        sesionManager = new SesionManager(this);
        usuarioController = new UsuarioController(this);

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

                // Autenticación real contra Base de Datos SQLite
                Usuario adultoMayor = usuarioController.iniciarSesion(rut, pass, "ADULTO_MAYOR");

                if (adultoMayor != null) {
                    // Guardar persistencia de sesión exitosa
                    sesionManager.crearSesion("ADULTO_MAYOR", adultoMayor.getNombre());
                    
                    Toast.makeText(LoginAdultoMayor.this, "¡Bienvenido! Abriendo panel principal...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginAdultoMayor.this, HubAdultoMayorActivity.class);
                    startActivity(intent);
                    finish();
                } else if (rut.equals("12345678-9") && pass.equals("1234")) {
                    // Credencial de emergencia/resguardo DEMO
                    sesionManager.crearSesion("ADULTO_MAYOR", "María González");
                    Toast.makeText(LoginAdultoMayor.this, "Ingreso modo DEMO...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginAdultoMayor.this, HubAdultoMayorActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginAdultoMayor.this, "RUT o contraseña incorrectos.", Toast.LENGTH_LONG).show();
                }
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

        // ------------------------------------------------------------------------
        // GESTIÓN DEL BOTÓN ATRÁS NATIVO DEL SISTEMA (OnBackPressedDispatcher)
        // ------------------------------------------------------------------------
        // En lugar de cerrar la app o tener un comportamiento errático, interceptamos
        // el botón "Atrás" físico/gestual para asegurar que retorne a MainActivity.
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Comportamiento idéntico al botón de la interfaz "Volver al Inicio"
                Intent intent = new Intent(LoginAdultoMayor.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}