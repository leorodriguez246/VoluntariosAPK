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

        edtEmail = findViewById(R.id.edtLoginEmail);
        edtPassword = findViewById(R.id.edtLoginPassword);
        btnIngresar = findViewById(R.id.btnLoginIngresar);
        txtIrARegistro = findViewById(R.id.txtIrARegistro);
        txtVolverAlMain = findViewById(R.id.txtVolverAlMain);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    edtEmail.setError("Ingrese su correo");
                    return;
                }
                if (pass.isEmpty()) {
                    edtPassword.setError("Ingrese su contraseña");
                    return;
                }

                if (email.equals("voluntario@gmail.com") && pass.equals("123456")) {
                    Toast.makeText(LoginVoluntarioActivity.this, "¡Ingreso exitoso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginVoluntarioActivity.this, HubVoluntarioActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginVoluntarioActivity.this, "Credenciales incorrectas de prueba (usar voluntario@gmail.com / 123456)", Toast.LENGTH_LONG).show();
                }
            }
        });

        txtVolverAlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginVoluntarioActivity.this, MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

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