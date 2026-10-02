package com.redvoluntarios.vistas.adultomayor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.redvoluntarios.R;
import com.redvoluntarios.vistas.principal.MainActivity;
import com.redvoluntarios.vistas.principal.SesionManager;

/**
 * PANTALLA: HubAdultoMayorActivity
 * ============================================================================
 * Panel de control principal del Adulto Mayor.
 * Implementa el BOTÓN DE PÁNICO utilizando los sensores de hardware del dispositivo:
 *  - Vibrador (Vibrator / VibrationEffect)
 *  - Llamadas telefónicas directas (Intent.ACTION_CALL)
 * Enseña a solicitar permisos en tiempo de ejecución de manera nativa.
 */
public class HubAdultoMayorActivity extends AppCompatActivity {

    private Button btnCerrarSesionAm;
    private Button btnPanico;
    private Button btnIrACrearSolicitud;

    private static final int REQUEST_CALL_PERMISSION = 1001;
    private final String NUMERO_EMERGENCIA = "tel:131"; // SAMU Chile por defecto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_adulto_mayor);

        // Prevenir solapamiento con la barra de navegación de Android (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vinculación de vistas
        btnCerrarSesionAm = findViewById(R.id.btnCerrarSesionAm);
        btnPanico = findViewById(R.id.btnPanico);
        btnIrACrearSolicitud = findViewById(R.id.btnIrACrearSolicitud);

        // BOTÓN DE PÁNICO: Llama a los métodos de vibración y llamada de emergencia
        btnPanico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hacerVibrarDispositivo();
                realizarLlamadaEmergencia();
            }
        });

        // Navegar a la pantalla de crear nueva solicitud de ayuda
        btnIrACrearSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HubAdultoMayorActivity.this, CrearSolicitudActivity.class);
                startActivity(intent);
            }
        });

        // Cerrar sesión
        btnCerrarSesionAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar los datos persistentes de SharedPreferences
                new SesionManager(HubAdultoMayorActivity.this).cerrarSesion();
                
                Toast.makeText(HubAdultoMayorActivity.this, "Sesión finalizada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HubAdultoMayorActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // ------------------------------------------------------------------------
        // GESTIÓN DEL BOTÓN ATRÁS NATIVO DEL SISTEMA
        // ------------------------------------------------------------------------
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Previene que retrocedan accidentalmente a la pantalla de login 
                // manteniendo la sesión "activa". Pide usar el botón oficial.
                Toast.makeText(HubAdultoMayorActivity.this, 
                    "Usa el botón de apagado para cerrar tu sesión y salir.", 
                    Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Activa el motor de vibración del teléfono como feedback háptico de emergencia.
     */
    private void hacerVibrarDispositivo() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Para Android 8+ (Oreo): Crea un efecto de vibración fuerte de 1000 milisegundos (1 seg)
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // Para versiones más antiguas
                vibrator.vibrate(1000);
            }
        }
    }

    /**
     * Verifica permisos y realiza la llamada telefónica usando ACTION_CALL.
     */
    private void realizarLlamadaEmergencia() {
        // 1. Comprobar si ya tenemos el permiso concedido
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 2. Si no, lo solicitamos en pantalla
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            // 3. Si ya lo tenemos, lanzamos la llamada directamente
            Intent intentLlamada = new Intent(Intent.ACTION_CALL);
            intentLlamada.setData(Uri.parse(NUMERO_EMERGENCIA));
            startActivity(intentLlamada);
            Toast.makeText(this, "Llamando a Emergencias...", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Escuchador del resultado del cuadro de diálogo de permisos.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El usuario otorgó el permiso, reintentamos la llamada
                realizarLlamadaEmergencia();
            } else {
                // El usuario denegó el permiso
                Toast.makeText(this, "Permiso de llamada denegado. No se puede realizar la acción.", Toast.LENGTH_LONG).show();
            }
        }
    }
}