package com.redvoluntarios.vistas.voluntario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.redvoluntarios.R;
import com.redvoluntarios.modelos.Solicitud;
import com.redvoluntarios.vistas.solicitud.DetalleSolicitudActivity;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;

/**
 * PANTALLA: HubVoluntarioActivity (Panel Principal del Voluntario)
 * ============================================================================
 * Muestra el panel central del voluntario con:
 *  - Encabezado con estado y botón de acceso al perfil.
 *  - ProgressBar con avance de meta de voluntariado.
 *  - RecyclerView con el listado dinámico de solicitudes entrantes.
 */
public class HubVoluntarioActivity extends AppCompatActivity {

    private ImageView imgAvatarHeader;
    private Button btnEditarPerfilHeader;
    private RecyclerView rvSolicitudesUrgentes;
    private ProgressBar pbMetaMensual;
    private TextView txtProgresoPorcentaje;
    private MaterialCardView cardHistorial1, cardHistorial2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hub_voluntario);

        // Prevenir solapamiento con la barra de navegación de Android (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vinculación de vistas
        imgAvatarHeader = findViewById(R.id.imgAvatarHeader);
        btnEditarPerfilHeader = findViewById(R.id.btnEditarPerfilHeader);
        rvSolicitudesUrgentes = findViewById(R.id.rvSolicitudesUrgentes);
        pbMetaMensual = findViewById(R.id.pbMetaMensual);
        txtProgresoPorcentaje = findViewById(R.id.txtProgresoPorcentaje);
        cardHistorial1 = findViewById(R.id.cardHistorial1);
        cardHistorial2 = findViewById(R.id.cardHistorial2);

        // Configuración de la barra de progreso (ProgressBar)
        pbMetaMensual.setProgress(75);
        txtProgresoPorcentaje.setText("75%");

        // Escuchador común para ir al Perfil de Usuario
        View.OnClickListener irAPerfilListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HubVoluntarioActivity.this, PerfilVoluntarioActivity.class);
                startActivity(intent);
            }
        };

        imgAvatarHeader.setOnClickListener(irAPerfilListener);
        btnEditarPerfilHeader.setOnClickListener(irAPerfilListener);

        // Configuración del RecyclerView: se asigna un LinearLayoutManager vertical
        rvSolicitudesUrgentes.setLayoutManager(new LinearLayoutManager(this));

        // Obtención de datos y vinculación con el adaptador (SolicitudAdapter)
        List<Solicitud> solicitudesList = obtenerSolicitudesSimuladas();
        SolicitudAdapter adapter = new SolicitudAdapter(this, solicitudesList);
        rvSolicitudesUrgentes.setAdapter(adapter);

        // Configuración de clics en el Historial
        View.OnClickListener abrirHistorialListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HubVoluntarioActivity.this, DetalleSolicitudActivity.class);
                intent.putExtra("MODO_HISTORIAL", true);
                startActivity(intent);
            }
        };

        cardHistorial1.setOnClickListener(abrirHistorialListener);
        cardHistorial2.setOnClickListener(abrirHistorialListener);

        // ------------------------------------------------------------------------
        // GESTIÓN DEL BOTÓN ATRÁS NATIVO DEL SISTEMA
        // ------------------------------------------------------------------------
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Previene regresar al login de manera insegura
                Toast.makeText(HubVoluntarioActivity.this, 
                    "Ve a 'Mi Perfil' para cerrar sesión y salir correctamente.", 
                    Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Método auxiliar para generar datos de prueba.
     */
    private List<Solicitud> obtenerSolicitudesSimuladas() {
        List<Solicitud> lista = new ArrayList<>();

        Solicitud sol1 = new Solicitud(1, 101, "María González (78 años)", "+56 9 1234 5678",
                "Av. Matta 450, Santiago", "URGENTE", "💊 Compra de Medicamentos",
                "Necesito receta de remedios en la Farmacia Ahumada de Av. Matta.",
                -33.4569, -70.6483, "31/08/2026 15:30", "PENDIENTE");

        Solicitud sol2 = new Solicitud(2, 102, "Pedro Soto (82 años)", "+56 9 8765 4321",
                "Calle San Diego 120, Santiago", "NORMAL", "🛒 Compras de Mercadería",
                "Ayuda para cargar bolsa con alimentos esenciales del supermercado.",
                -33.4510, -70.6512, "31/08/2026 14:15", "PENDIENTE");

        lista.add(sol1);
        lista.add(sol2);
        return lista;
    }
}