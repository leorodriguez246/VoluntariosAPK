package com.redvoluntarios.vistas.solicitud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.redvoluntarios.R;
import com.redvoluntarios.modelos.Review;
import com.redvoluntarios.vistas.principal.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * PANTALLA: EvaluarSolicitudActivity
 * ============================================================================
 * Sistema de calificación mediante estrellas (RatingBar) y comentarios de texto.
 * Permite registrar la experiencia y evaluar la reputación del usuario.
 */
public class EvaluarSolicitudActivity extends AppCompatActivity {

    private TextView txtNombrePersonaEvaluada, txtTextoCalificacion;
    private RatingBar rbPuntuacion;
    private EditText edtComentario;
    private Button btnEnviarReview, btnOmitirReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_evaluar_solicitud);

        // Vinculación de vistas
        txtNombrePersonaEvaluada = findViewById(R.id.txtNombrePersonaEvaluada);
        txtTextoCalificacion = findViewById(R.id.txtTextoCalificacion);
        rbPuntuacion = findViewById(R.id.rbPuntuacion);
        edtComentario = findViewById(R.id.edtComentarioReview);
        btnEnviarReview = findViewById(R.id.btnEnviarReview);
        btnOmitirReview = findViewById(R.id.btnOmitirReview);

        // Recepción de parámetros pasados mediante Intent Extras
        String nombreEvaluado = getIntent().getStringExtra("nombreEvaluado");
        String rolEvaluado = getIntent().getStringExtra("rolEvaluado");
        if (nombreEvaluado != null) {
            txtNombrePersonaEvaluada.setText("Evaluando a: " + nombreEvaluado);
        }

        // Escuchador de eventos del RatingBar para actualizar dinámicamente la etiqueta de calificación
        rbPuntuacion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating >= 4.5f) {
                    txtTextoCalificacion.setText("⭐ " + rating + " / 5.0 - Excelente trato");
                } else if (rating >= 3.0f) {
                    txtTextoCalificacion.setText("⭐ " + rating + " / 5.0 - Buen trato");
                } else if (rating >= 2.0f) {
                    txtTextoCalificacion.setText("⭐ " + rating + " / 5.0 - Trato regular");
                } else {
                    txtTextoCalificacion.setText("⭐ " + rating + " / 5.0 - Trato deficiente");
                }
            }
        });

        // Evento para enviar la reseña creada
        btnEnviarReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float estrellas = rbPuntuacion.getRating();
                String comentario = edtComentario.getText().toString().trim();
                String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                // Creación de la entidad Review
                Review nuevaReview = new Review(
                        1, 101, 201, 101,
                        "Juan Pérez", nombreEvaluado != null ? nombreEvaluado : "Usuario",
                        rolEvaluado != null ? rolEvaluado : "ADULTO_MAYOR",
                        estrellas, comentario, fechaActual
                );

                Toast.makeText(EvaluarSolicitudActivity.this,
                        "¡Gracias! Tu calificación de " + estrellas + " ⭐ ha sido registrada para mejorar la comunidad.",
                        Toast.LENGTH_LONG).show();

                // Limpiar pila y retornar a la pantalla principal
                Intent intent = new Intent(EvaluarSolicitudActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        btnOmitirReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}