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

public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.ViewHolder> {

    private final List<Solicitud> listaSolicitudes;
    private final Context context;

    public SolicitudAdapter(Context context, List<Solicitud> listaSolicitudes) {
        this.context = context;
        this.listaSolicitudes = listaSolicitudes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_solicitud, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Solicitud solicitud = listaSolicitudes.get(position);

        holder.txtCategoria.setText(solicitud.getCategoria());
        holder.txtPrioridad.setText(solicitud.getPrioridad());
        holder.txtCreador.setText("Solicitado por: " + solicitud.getNombreCreador());
        holder.txtDescripcion.setText("\"" + solicitud.getDescripcion() + "\"");

        if ("URGENTE".equalsIgnoreCase(solicitud.getPrioridad())) {
            holder.txtPrioridad.setText("🔴 URGENTE");
            holder.txtPrioridad.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else {
            holder.txtPrioridad.setText("🟡 NORMAL");
            holder.txtPrioridad.setTextColor(context.getResources().getColor(R.color.primary_blue));
        }

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