package com.redvoluntarios.vistas.voluntario;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.redvoluntarios.R;
import com.redvoluntarios.modelos.Solicitud;
import com.redvoluntarios.vistas.solicitud.DetalleSolicitudActivity;

import java.util.List;

/**
 * ADAPTADOR RECYCLERVIEW: SolicitudAdapter
 * ============================================================================
 * Conecta la lista de objetos `Solicitud` con la interfaz gráfica `item_solicitud.xml`.
 * Implementa el patrón `ViewHolder` para optimizar el rendimiento mediante la
 * reutilización eficiente de las vistas durante el desplazamiento (scrolling).
 */
public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.ViewHolder> {

    private final List<Solicitud> listaSolicitudes;
    private final Context context;

    public SolicitudAdapter(Context context, List<Solicitud> listaSolicitudes) {
        this.context = context;
        this.listaSolicitudes = listaSolicitudes;
    }

    /**
     * Instancia la vista XML individual (item_solicitud) y crea el ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_solicitud, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Asigna los datos del objeto Solicitud en la posición dada a las vistas del ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Solicitud solicitud = listaSolicitudes.get(position);

        holder.txtCategoria.setText(solicitud.getCategoria());
        holder.txtCreador.setText("Solicitado por: " + solicitud.getNombreCreador());
        holder.txtDescripcion.setText("\"" + solicitud.getDescripcion() + "\"");

        // Formato dinámico según el nivel de prioridad
        if ("URGENTE".equalsIgnoreCase(solicitud.getPrioridad())) {
            holder.txtPrioridad.setText("🔴 URGENTE");
            holder.txtPrioridad.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else {
            holder.txtPrioridad.setText("🟡 NORMAL");
            holder.txtPrioridad.setTextColor(context.getResources().getColor(R.color.primary_blue));
        }

        // Evento al presionar el botón de la tarjeta
        holder.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetalleSolicitudActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaSolicitudes != null ? listaSolicitudes.size() : 0;
    }

    /**
     * Clase ViewHolder interna que mantiene las referencias a los controles del ítem XML.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoria, txtPrioridad, txtCreador, txtDescripcion;
        Button btnAceptar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoria = itemView.findViewById(R.id.txtItemCategoria);
            txtPrioridad = itemView.findViewById(R.id.txtItemPrioridad);
            txtCreador = itemView.findViewById(R.id.txtItemCreador);
            txtDescripcion = itemView.findViewById(R.id.txtItemDescripcion);
            btnAceptar = itemView.findViewById(R.id.btnItemAceptar);
        }
    }
}