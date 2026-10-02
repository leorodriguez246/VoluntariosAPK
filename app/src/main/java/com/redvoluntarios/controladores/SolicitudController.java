package com.redvoluntarios.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.redvoluntarios.database.DbHelper;
import com.redvoluntarios.modelos.Solicitud;

import java.util.ArrayList;
import java.util.List;

/**
 * CONTROLADOR (MVC): SolicitudController
 * ============================================================================
 * Centraliza las consultas de Base de Datos para las solicitudes de ayuda.
 */
public class SolicitudController {

    private final DbHelper dbHelper;

    public SolicitudController(Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Inserta una nueva solicitud en SQLite (Usado por CrearSolicitudActivity)
     */
    public boolean crearSolicitud(Solicitud solicitud) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("id_creador", solicitud.getIdCreador());
        valores.put("nombre_creador", solicitud.getNombreCreador());
        valores.put("telefono_creador", solicitud.getTelefonoCreador());
        valores.put("direccion", solicitud.getDireccion());
        valores.put("prioridad", solicitud.getPrioridad());
        valores.put("categoria", solicitud.getCategoria());
        valores.put("descripcion", solicitud.getDescripcion());
        valores.put("latitud", solicitud.getLatitud());
        valores.put("longitud", solicitud.getLongitud());
        valores.put("fecha_hora", solicitud.getFechaHora());
        valores.put("estado", "PENDIENTE"); // Estado inicial obligatorio

        long resultadoId = db.insert("solicitudes", null, valores);
        db.close();

        return resultadoId != -1;
    }

    /**
     * Retorna todas las solicitudes que están en estado "PENDIENTE".
     * (Usado por HubVoluntarioActivity para llenar el RecyclerView)
     */
    public List<Solicitud> obtenerSolicitudesPendientes() {
        List<Solicitud> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM solicitudes WHERE estado = 'PENDIENTE' ORDER BY id_solicitud DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Solicitud s = new Solicitud();
                s.setIdSolicitud(cursor.getInt(cursor.getColumnIndexOrThrow("id_solicitud")));
                s.setIdCreador(cursor.getInt(cursor.getColumnIndexOrThrow("id_creador")));
                s.setNombreCreador(cursor.getString(cursor.getColumnIndexOrThrow("nombre_creador")));
                s.setTelefonoCreador(cursor.getString(cursor.getColumnIndexOrThrow("telefono_creador")));
                s.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow("direccion")));
                s.setPrioridad(cursor.getString(cursor.getColumnIndexOrThrow("prioridad")));
                s.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow("categoria")));
                s.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
                s.setLatitud(cursor.getDouble(cursor.getColumnIndexOrThrow("latitud")));
                s.setLongitud(cursor.getDouble(cursor.getColumnIndexOrThrow("longitud")));
                s.setFechaHora(cursor.getString(cursor.getColumnIndexOrThrow("fecha_hora")));
                s.setEstado(cursor.getString(cursor.getColumnIndexOrThrow("estado")));

                lista.add(s);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lista;
    }
}