package com.redvoluntarios.vistas.solicitud;

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

/**
 * PANTALLA: DetalleSolicitudActivity
 * ============================================================================
 * Muestra la información detallada de la solicitud aceptada.
 * Permite al voluntario reportar las tareas realizadas y simular la captura
 * de evidencia fotográfica antes de pasar a la evaluación.
 */
public class DetalleSolicitudActivity extends AppCompatActivity {

    private EditText edtTareasRealizadas;
    private Button btnAdjuntarFoto, btnFinalizarYCalificar, btnVolver;
    private TextView txtFotoEstado;
    private boolean fotoAdjuntada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalle_solicitud);

        // Vinculación de vistas
        edtTareasRealizadas = findViewById(R.id.edtTareasRealizadas);
        btnAdjuntarFoto = findViewById(R.id.btnAdjuntarFoto);
        btnFinalizarYCalificar = findViewById(R.id.btnFinalizarYCalificar);
        btnVolver = findViewById(R.id.btnVolverDetalle);
        txtFotoEstado = findViewById(R.id.txtFotoEstado);

        // Evento para simular adjunción de foto de evidencia
        btnAdjuntarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoAdjuntada = true;
                txtFotoEstado.setText("✓ Foto de evidencia adjuntada: comprobante_ayuda.jpg");
                txtFotoEstado.setTextColor(getResources().getColor(R.color.secondary_green));
                Toast.makeText(DetalleSolicitudActivity.this,
                        "Evidencia fotográfica adjuntada con éxito", Toast.LENGTH_SHORT).show();
            }
        });

        // Evento para finalizar la tarea y abrir la evaluación
        btnFinalizarYCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tareas = edtTareasRealizadas.getText().toString().trim();
                if (tareas.isEmpty()) {
                    edtTareasRealizadas.setError("Por favor reporta las tareas realizadas para la persona");
                    return;
                }

                // Uso de Intent con extras para pasar parámetros a la siguiente actividad
                Intent intent = new Intent(DetalleSolicitudActivity.this, EvaluarSolicitudActivity.class);
                intent.putExtra("nombreEvaluado", "María González");
                intent.putExtra("rolEvaluado", "ADULTO_MAYOR");
                startActivity(intent);
                finish();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}