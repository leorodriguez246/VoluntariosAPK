package com.redvoluntarios.vistas.adultomayor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.redvoluntarios.R;
import com.redvoluntarios.modelos.Solicitud;
import com.redvoluntarios.vistas.principal.MainActivity;
import androidx.activity.OnBackPressedCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * PANTALLA: CrearSolicitudActivity
 * ============================================================================
 * Formulario accesible para la creación de solicitudes de ayuda.
 * Implementa Geolocalización nativa mediante LocationManager para obtener la
 * ubicación actual del Adulto Mayor.
 */
public class CrearSolicitudActivity extends AppCompatActivity {

    private RadioGroup rgPrioridad;
    private RadioButton rbNormal, rbUrgente;
    private Spinner spCategoria;
    private EditText edtDescripcion;
    private TextView txtUbicacionActual;
    private Button btnEnviarSolicitud, btnVolverInicio;

    private static final int REQUEST_LOCATION_PERMISSION = 1002;
    private double latitudCapturada = -33.4569; // Valor por defecto (Santiago)
    private double longitudCapturada = -70.6483;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_solicitud);

        // Prevenir solapamiento con la barra de navegación de Android (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rgPrioridad = findViewById(R.id.rgPrioridad);
        rbNormal = findViewById(R.id.rbPrioridadNormal);
        rbUrgente = findViewById(R.id.rbPrioridadUrgente);
        spCategoria = findViewById(R.id.spCategoria);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        txtUbicacionActual = findViewById(R.id.txtUbicacionActual);
        btnEnviarSolicitud = findViewById(R.id.btnEnviarSolicitud);
        btnVolverInicio = findViewById(R.id.btnVolverInicioAm);

        String[] categorias = {
                "💊 Compra de Medicamentos",
                "🛒 Compras de Mercadería",
                "🤝 Compañía y Conversación",
                "📋 Trámites y Banco"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adapter);

        // Obtener ubicación al abrir la pantalla
        obtenerUbicacionGPS();

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

                Solicitud nuevaSolicitud = new Solicitud(
                        1, 101, "María González", "+56 9 1234 5678",
                        "Ubicación actual de María", prioridad, categoriaSeleccionada, desc,
                        latitudCapturada, longitudCapturada, fechaActual, "PENDIENTE"
                );

                Toast.makeText(CrearSolicitudActivity.this,
                        "¡Solicitud enviada! Conectando con voluntarios de tu zona...",
                        Toast.LENGTH_LONG).show();

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

        // ------------------------------------------------------------------------
        // GESTIÓN DEL BOTÓN ATRÁS NATIVO DEL SISTEMA
        // ------------------------------------------------------------------------
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Simplemente finalizamos esta pantalla para que se vuelva a ver
                // el HubAdultoMayorActivity que quedó en segundo plano.
                finish();
            }
        });
    }

    /**
     * Comprueba permisos y obtiene la última ubicación conocida.
     */
    private void obtenerUbicacionGPS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

                if (location != null) {
                    latitudCapturada = location.getLatitude();
                    longitudCapturada = location.getLongitude();
                    txtUbicacionActual.setText("📍 Ubicación actual: " + latitudCapturada + ", " + longitudCapturada + " (GPS Activo)");
                } else {
                    txtUbicacionActual.setText("📍 Buscando ubicación GPS...");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacionGPS();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado. Se usará una ubicación predeterminada.", Toast.LENGTH_LONG).show();
            }
        }
    }
}