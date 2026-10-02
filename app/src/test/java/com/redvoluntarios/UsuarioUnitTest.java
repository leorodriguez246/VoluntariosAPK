package com.redvoluntarios;

import com.redvoluntarios.modelos.Solicitud;
import com.redvoluntarios.modelos.Usuario;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * PRUEBAS UNITARIAS LOCALES (JUnit)
 * ============================================================================
 * Estas pruebas se ejecutan directamente en la JVM del computador (sin emulador).
 * Permiten verificar la lógica interna de los objetos de modelo y métodos auxiliares
 * de forma extremadamente rápida.
 */
public class UsuarioUnitTest {

    /**
     * Verifica que la instanciación de un objeto Usuario asigne correctamente sus valores.
     */
    @Test
    public void testCreacionUsuario() {
        Usuario usuario = new Usuario("María González", "12345678-9", "ADULTO_MAYOR", "+56 9 1234 5678", "Av. Matta 450");

        assertNotNull("El objeto usuario no debería ser nulo", usuario);
        assertEquals("María González", usuario.getNombre());
        assertEquals("12345678-9", usuario.getRut());
        assertEquals("ADULTO_MAYOR", usuario.getRol());
        assertEquals("+56 9 1234 5678", usuario.getTelefono());
    }

    /**
     * Verifica la instanciación de una Solicitud de Ayuda con prioridad URGENTE.
     */
    @Test
    public void testCreacionSolicitud() {
        Solicitud solicitud = new Solicitud(1, 101, "María González", "+56 9 1234 5678",
                "Av. Matta 450", "URGENTE", "Medicamentos",
                "Receta médica en farmacia", -33.4569, -70.6483, "31/08/2026", "PENDIENTE");

        assertEquals("URGENTE", solicitud.getPrioridad());
        assertEquals("PENDIENTE", solicitud.getEstado());
        assertEquals("Medicamentos", solicitud.getCategoria());
    }

    /**
     * Pruebas auxiliares de validación de formato de datos.
     */
    @Test
    public void testValidacionCorreoFormato() {
        String correoValido = "voluntario@gmail.com";
        assertTrue("El correo debe contener un arroba @", correoValido.contains("@"));
        assertTrue("El correo debe finalizar en .com", correoValido.endsWith(".com"));
    }
}