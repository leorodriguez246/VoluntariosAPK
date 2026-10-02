package com.redvoluntarios.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.redvoluntarios.database.DbHelper;
import com.redvoluntarios.modelos.Usuario;

/**
 * CONTROLADOR (MVC): UsuarioController
 * ============================================================================
 * Centraliza toda la lógica de negocio y las consultas SQL (CRUD) asociadas
 * a la tabla "usuarios". Aisla la complejidad de la base de datos de las Vistas (Activities).
 */
public class UsuarioController {

    private final DbHelper dbHelper;

    public UsuarioController(Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Inserta un nuevo registro de usuario en la base de datos local SQLite.
     * @param usuario Objeto Usuario poblado desde la interfaz.
     * @param password Contraseña de seguridad (En este alcance académico, en texto plano).
     * @return true si la inserción fue exitosa, false si falló (ej. RUT duplicado).
     */
    public boolean registrarUsuario(Usuario usuario, String password) {
        // Solicitamos acceso en modo ESCRITURA a la base de datos
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ContentValues actúa como un diccionario (Clave=Columna, Valor=Dato)
        ContentValues valores = new ContentValues();
        valores.put("nombre", usuario.getNombre());
        valores.put("rut", usuario.getRut()); // Para el voluntario será el Email
        valores.put("rol", usuario.getRol());
        valores.put("telefono", usuario.getTelefono());
        valores.put("direccion", usuario.getDireccion());
        valores.put("password", password);

        // Retorna el ID de la nueva fila, o -1 si hubo un error (como restricción UNIQUE)
        long resultadoId = db.insert("usuarios", null, valores);
        db.close();

        return resultadoId != -1;
    }

    /**
     * Valida la existencia de las credenciales en la base de datos para iniciar sesión.
     * @param rutOEmail RUT (Adulto Mayor) o Email (Voluntario).
     * @param password Contraseña ingresada.
     * @param rolEsperado Permite restringir que un Voluntario no intente entrar por la pantalla de Adulto Mayor.
     * @return Un objeto Usuario si la autenticación es correcta, o null si falla.
     */
    public Usuario iniciarSesion(String rutOEmail, String password, String rolEsperado) {
        // Solicitamos acceso en modo LECTURA a la base de datos
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Usuario usuarioLogueado = null;

        String query = "SELECT * FROM usuarios WHERE rut = ? AND password = ? AND rol = ?";
        String[] parametros = { rutOEmail, password, rolEsperado };

        // El Cursor apunta a los resultados (filas) devueltos por la consulta SQL
        Cursor cursor = db.rawQuery(query, parametros);

        // Si moveToFirst() es true, significa que encontramos exactamente al usuario
        if (cursor.moveToFirst()) {
            usuarioLogueado = new Usuario();
            usuarioLogueado.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario")));
            usuarioLogueado.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            usuarioLogueado.setRut(cursor.getString(cursor.getColumnIndexOrThrow("rut")));
            usuarioLogueado.setRol(cursor.getString(cursor.getColumnIndexOrThrow("rol")));
            usuarioLogueado.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow("telefono")));
            usuarioLogueado.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow("direccion")));
        }

        cursor.close();
        db.close();

        return usuarioLogueado;
    }
}