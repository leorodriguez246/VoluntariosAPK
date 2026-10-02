package com.redvoluntarios.vistas.solicitud;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.OnBackPressedCallback;

import com.redvoluntarios.R;

/**
 * PANTALLA: DetalleSolicitudActivity
 * ============================================================================
 * Muestra la información de la solicitud e implementa una máquina de estados:
 *  1. NUEVA: Mostrar botones Aceptar/Rechazar.
 *  2. EN PROCESO: Mostrar campos de tareas y cámara.
 *  3. HISTORIAL: Modo solo lectura.
 */
public class DetalleSolicitudActivity extends AppCompatActivity {

    private LinearLayout containerAccionesIniciales;
    private LinearLayout containerEvidencias;
    
    private Button btnAceptarAyuda, btnRechazarAyuda;
    private EditText edtTareasRealizadas;
    private Button btnAdjuntarFoto, btnFinalizarYCalificar, btnVolver;
    private TextView txtFotoEstado;
    private ImageView imgFotoEvidencia;
    private WebView webViewMapa;
    private boolean fotoAdjuntada = false;

    // Lanzador para la aplicación de Cámara
    private ActivityResultLauncher<Intent> camaraLauncher;
    private static final int REQUEST_CAMERA_PERMISSION = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalle_solicitud);

        // Prevenir solapamiento con la barra de navegación de Android (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vinculación de vistas y contenedores de la máquina de estados
        containerAccionesIniciales = findViewById(R.id.containerAccionesIniciales);
        containerEvidencias = findViewById(R.id.containerEvidencias);
        
        btnAceptarAyuda = findViewById(R.id.btnAceptarAyuda);
        btnRechazarAyuda = findViewById(R.id.btnRechazarAyuda);
        
        edtTareasRealizadas = findViewById(R.id.edtTareasRealizadas);
        btnAdjuntarFoto = findViewById(R.id.btnAdjuntarFoto);
        btnFinalizarYCalificar = findViewById(R.id.btnFinalizarYCalificar);
        btnVolver = findViewById(R.id.btnVolverDetalle);
        txtFotoEstado = findViewById(R.id.txtFotoEstado);
        imgFotoEvidencia = findViewById(R.id.imgFotoEvidencia);
        webViewMapa = findViewById(R.id.webViewMapa);

        // ====================================================================
        // CONFIGURACIÓN MAPA (WEBVIEW NATIVO)
        // ====================================================================
        // Configura el mapa embebido apuntando a la dirección simulada
        double lat = -33.4569;
        double lon = -70.6483;
        String iframeMapa = "<iframe width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" " +
                "src=\"https://maps.google.com/maps?q=" + lat + "," + lon + "&hl=es&z=15&output=embed\"></iframe>";
        
        WebSettings webSettings = webViewMapa.getSettings();
        
        // Habilitar características DOM necesarias para Google Maps
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        
        webViewMapa.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                // Si el usuario presiona botones del mapa (ej. abrir en Maps, ver logo de Google)
                if (url.startsWith("http") || url.startsWith("https") || url.startsWith("intent://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(DetalleSolicitudActivity.this, "No se pudo abrir el enlace", Toast.LENGTH_SHORT).show();
                    }
                    return true; // Indicamos que ya manejamos nosotros el clic
                }
                return false;
            }
        });
        
        webViewMapa.loadData(iframeMapa, "text/html", "utf-8");

        // ====================================================================
        // MÁQUINA DE ESTADOS VISUAL
        // ====================================================================
        boolean esHistorial = getIntent().getBooleanExtra("MODO_HISTORIAL", false);

        if (esHistorial) {
            // MODO HISTORIAL (Cumplida)
            containerAccionesIniciales.setVisibility(View.GONE);
            containerEvidencias.setVisibility(View.GONE);
            btnFinalizarYCalificar.setVisibility(View.GONE);
        } else {
            // MODO NUEVA SOLICITUD
            containerAccionesIniciales.setVisibility(View.VISIBLE);
            containerEvidencias.setVisibility(View.GONE);
            btnFinalizarYCalificar.setVisibility(View.GONE);
        }

        // EVENTOS DE CAMBIO DE ESTADO
        btnAceptarAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar a EN PROCESO
                containerAccionesIniciales.setVisibility(View.GONE);
                containerEvidencias.setVisibility(View.VISIBLE);
                btnFinalizarYCalificar.setVisibility(View.VISIBLE);
                Toast.makeText(DetalleSolicitudActivity.this, "¡Solicitud Aceptada! Ahora puedes reportar avances.", Toast.LENGTH_LONG).show();
            }
        });

        btnRechazarAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetalleSolicitudActivity.this, "Solicitud rechazada", Toast.LENGTH_SHORT).show();
                finish(); // Retornar al Hub
            }
        });


        // ====================================================================
        // MANEJO DE EVIDENCIA (CÁMARA)
        // ====================================================================
        camaraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle extras = result.getData().getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            
                            imgFotoEvidencia.setImageBitmap(imageBitmap);
                            imgFotoEvidencia.setVisibility(View.VISIBLE);
                            fotoAdjuntada = true;

                            txtFotoEstado.setText("✓ Evidencia capturada correctamente.");
                            txtFotoEstado.setTextColor(getResources().getColor(R.color.secondary_green));
                        }
                    }
                }
        );

        btnAdjuntarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar permisos de cámara en tiempo de ejecución (Android 6.0+)
                if (ContextCompat.checkSelfPermission(DetalleSolicitudActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetalleSolicitudActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    abrirCamaraSegura();
                }
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

                if (!fotoAdjuntada) {
                    Toast.makeText(DetalleSolicitudActivity.this, "Se recomienda adjuntar una foto de evidencia", Toast.LENGTH_SHORT).show();
                }

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

        // ------------------------------------------------------------------------
        // GESTIÓN DEL BOTÓN ATRÁS NATIVO DEL SISTEMA
        // ------------------------------------------------------------------------
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    /**
     * Método seguro para abrir la cámara evitando ActivityNotFoundException en Android 11+
     */
    private void abrirCamaraSegura() {
        Intent tomaFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            camaraLauncher.launch(tomaFotoIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No se encontró ninguna aplicación de cámara", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
                abrirCamaraSegura();
            } else {
                // Permiso denegado
                Toast.makeText(this, "Debes otorgar el permiso de cámara para adjuntar evidencias.", Toast.LENGTH_LONG).show();
            }
        }
    }
}