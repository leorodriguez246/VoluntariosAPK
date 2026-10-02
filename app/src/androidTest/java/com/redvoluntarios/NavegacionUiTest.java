package com.redvoluntarios;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.redvoluntarios.vistas.principal.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * PRUEBAS INSTRUMENTADAS / INTERFAZ DE USUARIO (Espresso + JUnit)
 * ============================================================================
 * Estas pruebas requieren un dispositivo físico o emulador conectado.
 * Espresso simula automáticamente las interacciones del usuario (escribir texto,
 * presionar botones, desplazar vistas) y verifica que los componentes correctos
 * se desplieguen en pantalla.
 */
@RunWith(AndroidJUnit4.class)
public class NavegacionUiTest {

    /**
     * Regla que indica a JUnit lanzar la actividad principal (MainActivity)
     * antes de ejecutar cada prueba individual.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Verifica que los botones principales de la pantalla de inicio estén visibles.
     */
    @Test
    public void testVerificarBotonesInicio() {
        // Buscar el botón por su ID y verificar que esté visible en pantalla
        onView(withId(R.id.btnRolMayor)).check(matches(isDisplayed()));
        onView(withId(R.id.btnRolVoluntario)).check(matches(isDisplayed()));
    }

    /**
     * Simula el clic en el botón de Voluntarios y verifica el cambio de pantalla hacia el Login.
     */
    @Test
    public void testNavegacionLoginVoluntario() {
        // Hacer clic en el botón "Quiero ser Voluntario"
        onView(withId(R.id.btnRolVoluntario)).perform(click());

        // Comprobar que en la nueva pantalla aparezca el título de Login
        onView(withId(R.id.txtLoginTitulo)).check(matches(isDisplayed()));
    }

    /**
     * Prueba completa de ingreso de credenciales y navegación al Hub de Voluntarios.
     */
    @Test
    public void testFlujoLoginVoluntarioExitoso() {
        // 1. Abrir la pantalla de Login
        onView(withId(R.id.btnRolVoluntario)).perform(click());

        // 2. Simular la escritura del correo electrónico de prueba
        onView(withId(R.id.edtLoginEmail))
                .perform(typeText("voluntario@gmail.com"), closeSoftKeyboard());

        // 3. Simular la escritura de la contraseña de prueba
        onView(withId(R.id.edtLoginPassword))
                .perform(typeText("123456"), closeSoftKeyboard());

        // 4. Hacer clic en el botón "Iniciar Sesión"
        onView(withId(R.id.btnLoginIngresar)).perform(click());

        // 5. Verificar que se despliegue con éxito el nombre del voluntario en la cabecera del Hub
        onView(withId(R.id.txtUsuarioNombre)).check(matches(isDisplayed()));
    }
}