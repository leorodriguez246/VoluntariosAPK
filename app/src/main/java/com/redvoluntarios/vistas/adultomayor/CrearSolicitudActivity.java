package com.redvoluntarios.vistas.adultomayor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.redvoluntarios.R;
import com.redvoluntarios.modelos.Solicitud;
import com.redvoluntarios.vistas.principal.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrearSolicitudActivity extends AppCompatActivity {

    private RadioGroup rgPrioridad;
    private RadioButton rbNormal, rbUrgente;
    private Spinner spCategoria;
    private EditText edtDescripcion;
    private Button btnEnviarSolicitud, btnVolverInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_solicitud);

        rgPrioridad = findViewById(R.id.rgPrioridad);
        rbNormal = findViewById(R.id.rbPrioridadNormal);
        rbUrgente = findViewById(R.id.rbPrioridadUrgente);
        spCategoria = findViewById(R.id.spCategoria);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        btnEnviarSolicitud = findViewById(R.id.btnEnviarSolicitud);
        btnVolverInicio = findViewById(R.id.btnVolverInicioAm);

        // Opciones del Spinner de categorías
        String[] categorias = {
                "💊 Compra de Medicamentos",
                "🛒 Compras de Mercadería",
                "🤝 Compañía y Conversación",
                "📋 Trámites y Banco"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adapter);

        btnEnviarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = edtDescripcion.getText().toString().trim();
                if (desc.isEmpty()) {
                    edtDescripcion.setError("Por favor describe brevemente qué necesitas");
                    return;
                }

                String prioridad = rbUrgente.isChecked() ? "URGENTE" : "NORMAL";
                String categoriaSeleccionada = spCategoria.getSelectedItem().toString();
                String fechaActual = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

                // Crear objeto solicitud
                Solicitud nuevaSolicitud = new Solicitud(
                        1, 101, "María González", "+56 9 1234 5678",
                        "Av. Matta 450, Santiago", prioridad, categoriaSeleccionada, desc,
                        -33.4569, -70.6483, fechaActual, "PENDIENTE"
                );

                Toast.makeText(CrearSolicitudActivity.this,
                        "¡Solicitud enviada! Conectando con voluntarios de tu zona...",
                        Toast.LENGTH_LONG).show();

                // Regresar a la pantalla inicial
                Intent intent = new Intent(CrearSolicitudActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        btnVolverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}