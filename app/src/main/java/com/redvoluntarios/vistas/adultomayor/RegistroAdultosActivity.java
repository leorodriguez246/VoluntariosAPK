package com.redvoluntarios.vistas.adultomayor;

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

public class RegistroAdultosActivity extends AppCompatActivity {

    private EditText edtNombre, edtRut, edtTelefono, edtDireccion, edtPassword;
    private CheckBox chkTerminosAm;
    private Button btnRegistrar;
    private TextView txtVolverLogin;
    private TextView txtVolverAlMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_adultos);

        edtNombre = findViewById(R.id.edtRegAmNombre);
        edtRut = findViewById(R.id.edtRegAmRut);
        edtTelefono = findViewById(R.id.edtRegAmTelefono);
        edtDireccion = findViewById(R.id.edtRegAmDireccion);
        edtPassword = findViewById(R.id.edtRegAmPassword);
        chkTerminosAm = findViewById(R.id.chkTerminosAm);
        btnRegistrar = findViewById(R.id.btnRegAmRegistrar);
        txtVolverLogin = findViewById(R.id.txtVolverAlLoginAm);
        txtVolverAlMain = findViewById(R.id.txtVolverAlMainAm);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombre.getText().toString().trim();
                String rut = edtRut.getText().toString().trim();
                String fono = edtTelefono.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                if (nombre.isEmpty()) { edtNombre.setError("Nombre obligatorio"); return; }
                if (rut.isEmpty()) { edtRut.setError("RUT obligatorio"); return; }
                if (fono.isEmpty()) { edtTelefono.setError("Teléfono obligatorio"); return; }
                if (pass.isEmpty() || pass.length() < 4) {
                    edtPassword.setError("Contraseña mínima de 4 caracteres");
                    return;
                }

                if (!chkTerminosAm.isChecked()) {
                    Toast.makeText(RegistroAdultosActivity.this,
                            "Debe aceptar los términos de uso asistido",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(RegistroAdultosActivity.this, "¡Registro exitoso! Ya puedes ingresar.", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        txtVolverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtVolverAlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroAdultosActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}