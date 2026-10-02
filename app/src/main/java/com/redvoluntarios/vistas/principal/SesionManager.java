package com.redvoluntarios.vistas.principal;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * CLASE DE UTILIDAD: SesionManager
 * ============================================================================
 * Gestiona la persistencia de las sesiones de los usuarios de manera nativa
 * usando la API SharedPreferences de Android (almacenamiento ligero clave-valor).
 * 
 * Permite que los usuarios no tengan que iniciar sesión cada vez que abren la app.
 */
public class SesionManager {

    private static final String PREF_NAME = "RedCuidarSesion";
    private static final String KEY_LOGUEADO = "estaLogueado";
    private static final String KEY_ROL_USUARIO = "rolUsuario";
    private static final String KEY_NOMBRE_USUARIO = "nombreUsuario";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SesionManager(Context context) {
        // Modo privado asegura que solo esta app pueda leer estas preferencias
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Guarda la sesión del usuario tras un login exitoso.
     * @param rol 'ADULTO_MAYOR' o 'VOLUNTARIO'
     * @param nombre Nombre del usuario a mostrar en pantalla
     */
    public void crearSesion(String rol, String nombre) {
        editor.putBoolean(KEY_LOGUEADO, true);
        editor.putString(KEY_ROL_USUARIO, rol);
        editor.putString(KEY_NOMBRE_USUARIO, nombre);
        editor.apply(); // Guarda de forma asíncrona (no bloquea la pantalla)
    }

    /**
     * @return true si existe una sesión activa guardada.
     */
    public boolean verificarSesionActiva() {
        return sharedPreferences.getBoolean(KEY_LOGUEADO, false);
    }

    /**
     * @return El rol del usuario actualmente conectado.
     */
    public String obtenerRolUsuario() {
        return sharedPreferences.getString(KEY_ROL_USUARIO, "");
    }

    /**
     * @return El nombre del usuario conectado.
     */
    public String obtenerNombreUsuario() {
        return sharedPreferences.getString(KEY_NOMBRE_USUARIO, "Usuario");
    }

    /**
     * Elimina todos los datos guardados en las preferencias (Logout).
     */
    public void cerrarSesion() {
        editor.clear();
        editor.apply();
    }
}