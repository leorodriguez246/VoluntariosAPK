package com.redvoluntarios.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * CAPA DE DATOS: DbHelper (SQLiteOpenHelper)
 * ============================================================================
 * Administrador central de la base de datos local del teléfono.
 * Se encarga de crear el archivo "RedCuidar.db" y estructurar las tablas
 * iniciales la primera vez que la aplicación se instala o ejecuta.
 */
public class DbHelper extends SQLiteOpenHelper {

    // Configuración general
    private static final String DATABASE_NAME = "RedCuidar.db";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL: Creación de la tabla USUARIOS
    private static final String CREATE_TABLE_USUARIOS = 
        "CREATE TABLE usuarios (" +
        "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "nombre TEXT NOT NULL, " +
        "rut TEXT UNIQUE NOT NULL, " +
        "rol TEXT NOT NULL, " +
        "telefono TEXT, " +
        "direccion TEXT, " +
        "password TEXT NOT NULL" +
        ");";

    // Sentencia SQL: Creación de la tabla SOLICITUDES
    private static final String CREATE_TABLE_SOLICITUDES = 
        "CREATE TABLE solicitudes (" +
        "id_solicitud INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "id_creador INTEGER NOT NULL, " +
        "nombre_creador TEXT, " +
        "telefono_creador TEXT, " +
        "direccion TEXT, " +
        "prioridad TEXT, " +
        "categoria TEXT, " +
        "descripcion TEXT, " +
        "latitud REAL, " +
        "longitud REAL, " +
        "fecha_hora TEXT, " +
        "estado TEXT, " +
        "id_voluntario INTEGER, " +
        "nombre_voluntario TEXT, " +
        "tareas_realizadas TEXT, " +
        "foto_evidencia TEXT, " +
        "FOREIGN KEY(id_creador) REFERENCES usuarios(id_usuario) ON DELETE CASCADE" +
        ");";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Se ejecuta de forma automática ÚNICAMENTE la primera vez que se solicita
     * la base de datos y esta no existe físicamente en el teléfono.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ejecución de los scripts de creación de tablas
        db.execSQL(CREATE_TABLE_USUARIOS);
        db.execSQL(CREATE_TABLE_SOLICITUDES);
    }

    /**
     * Se ejecuta automáticamente cuando se detecta que DATABASE_VERSION fue 
     * incrementado, permitiendo actualizar la estructura sin que la app colapse.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminamos las tablas viejas de forma destructiva (solo recomendado para desarrollo/etapas tempranas)
        db.execSQL("DROP TABLE IF EXISTS solicitudes");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        
        // Volvemos a crear el esquema limpio
        onCreate(db);
    }
}