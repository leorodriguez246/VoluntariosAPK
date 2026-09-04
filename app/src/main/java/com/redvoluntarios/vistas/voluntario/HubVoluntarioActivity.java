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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.redvoluntarios.R;
import com.redvoluntarios.modelos.Solicitud;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hub_voluntario);

        // Vinculación de vistas
        imgAvatarHeader = findViewById(R.id.imgAvatarHeader);
        btnEditarPerfilHeader = findViewById(R.id.btnEditarPerfilHeader);
        rvSolicitudesUrgentes = findViewById(R.id.rvSolicitudesUrgentes);
        pbMetaMensual = findViewById(R.id.pbMetaMensual);
        txtProgresoPorcentaje = findViewById(R.id.txtProgresoPorcentaje);

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