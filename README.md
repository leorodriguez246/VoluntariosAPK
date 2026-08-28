# Red Cuidar: App de Voluntariado Comunitario para Adultos Mayores

**Asignatura:** Programación Android (IEI-093)  
**Institución:** Instituto Profesional Santo Tomás  
**Metodología:** Aprendizaje Basado en Problemas (ABP)  
**Entorno de Desarrollo:** Android Studio Koala (2024.1.1)  
**Lenguaje Principal:** Java  
**Sistema de Compilación:** Groovy DSL (build.gradle)

---

## 📋 Descripción del Proyecto
**Red Cuidar** es una plataforma móvil de impacto social e inclusivo diseñada para conectar de forma directa y segura a adultos mayores que requieren asistencia en sus tareas cotidianas (compras, trámites, compañía, asistencia médica básica) con una red de usuarios voluntarios registrados en su misma comuna o territorio. 

El proyecto integra de manera transversal la **perspectiva de género** (visibilizando y aliviando la sobrecarga en las labores de cuidado que históricamente han recaído de forma desproporcionada en mujeres) y la **inclusión digital** mediante interfaces simplificadas con asistencia visual para personas de la tercera edad.

---

## 🛠️ Ruta de Desarrollo Progresivo (Semestre 2026)

El proyecto se construye de manera incremental durante las tres unidades de la asignatura:

*   **Unidad I (Semanas 1-5):** Diseño de la interfaz de usuario accesible utilizando `ConstraintLayout` (con sistemas de anclas para evitar anidamientos). Creación del flujo básico de pantallas, botones, etiquetas de texto e inicialización obligatoria del repositorio local en **Git**.
*   **Unidad II (Semanas 6-12):** Implementación de la lógica interactiva. Transmisión de datos entre pantallas (`Intents`), ejecución asíncrona de alarmas médicas (`Threads`), geolocalización de solicitudes mediante **GPS y Google Maps**, uso de cámara para evidencias (**Multimedia**), mitigación de vulnerabilidades de seguridad (*Security Tips* de Google) y almacenamiento local estructurado en **SQLite**.
*   **Unidad III (Semanas 13-18):** Conectividad avanzada e IoT. Sincronización remota y autenticación de usuarios con **Firebase (Auth y Firestore)** e integración de un botón de pánico de telemetría instantánea utilizando el protocolo ligero **MQTT**.

---

## 🏛️ Estructura de la Aplicación (Estructura de Paquetes en Java)

Para mantener el código limpio y mantenible, utilizaremos la arquitectura clásica por paquetes recomendada en la asignatura:

```text
cl.santotomas.redcuidar/
│
├── model/                  # Clases de Entidad (Datos)
│   ├── Usuario.java        # Atributos de Voluntarios y Adultos Mayores
│   └── Solicitud.java      # Datos de la tarea de ayuda (ID, descripción, GPS, estado)
│
├── database/               # Persistencia de Datos Local
│   └── DbHelper.java       # Manejador de Base de Datos SQLite (operaciones CRUD)
│
└── views/                  # Controladores de Pantalla (Activities)
    ├── LoginActivity.java  # Control de acceso inicial (Java/XML)
    ├── RegistroActivity.java # Creación de cuentas (Adulto Mayor o Voluntario)
    │
    ├── elderly/            # Vistas específicas para el Adulto Mayor
    │   ├── ElderlyMainActivity.java   # Menú simplificado (Botones grandes)
    │   └── CrearSolicitudActivity.java # Formulario para solicitar ayuda
    │
    └── volunteer/          # Vistas específicas para el Voluntario
        ├── VolunteerMainActivity.java # Listado dinámico de ayuda (RecyclerView)
        └── MapaAyudaActivity.java     # Visualización de la tarea en el mapa (GPS)
```

---

## 🖥️ Pantallas del Proyecto (Vistas XML de la Unidad I)

1.  **`activity_login.xml`:** Pantalla de acceso. Contiene campos para RUT/Email, contraseña, un botón de ingreso y un enlace para registro.
2.  **`activity_registro.xml`:** Formulario de registro que incluye un selector (`RadioButton`) para definir el rol del usuario: "Adulto Mayor" o "Voluntario".
3.  **`activity_elderly_main.xml`:** Panel del adulto mayor diseñado bajo principios de accesibilidad. Botones grandes con soporte de texto a voz y contrastes marcados para solicitar ayuda inmediata o activar el botón de pánico.
4.  **`activity_crear_solicitud.xml`:** Formulario intuitivo con un selector (`Spinner`) para elegir la categoría de ayuda (ej: "Medicamentos", "Compras", "Compañía") y campos para descripción del problema.
5.  **`activity_volunteer_main.xml`:** Panel del voluntario que implementa un contenedor dinámico (`RecyclerView` con `CardView`) para mostrar de forma fluida las solicitudes activas de la comunidad.

---

## 🗄️ Esquema de la Base de Datos Local (SQLite - Unidad II)

La base de datos local `RedCuidar.db` se compone de las siguientes tablas fundamentales:

### Tabla: `USUARIOS`
*   `id_usuario` (INTEGER, PRIMARY KEY, AUTOINCREMENT)
*   `nombre` (TEXT, NOT NULL)
*   `rut` (TEXT, UNIQUE, NOT NULL)
*   `rol` (TEXT) -- 'ELDERLY' o 'VOLUNTEER'
*   `telefono` (TEXT)

### Tabla: `SOLICITUDES`
*   `id_solicitud` (INTEGER, PRIMARY KEY, AUTOINCREMENT)
*   `id_creador` (INTEGER, FOREIGN KEY -> USUARIOS)
*   `categoria` (TEXT)
*   `descripcion` (TEXT)
*   `latitud` (REAL)
*   `longitud` (REAL)
*   `estado` (TEXT) -- 'PENDIENTE', 'ACEPTADA', 'FINALIZADA'

---

## ⚙️ Configuración y Ejecución del Proyecto

1.  **Clonar el repositorio:**
    ```bash
    git clone <url-de-tu-repositorio-en-github>
    ```
2.  **Abrir en Android Studio:**
    *   Selecciona "Open an Existing Project".
    *   Busca la carpeta raíz del proyecto.
3.  **Verificar el SDK y la JVM:**
    *   Asegúrate de tener instalado el SDK de Android compatible con la API mínima seleccionada (se recomienda API 26: Android 8.0 Oreo o superior para garantizar compatibilidad con mapas y sensores).
    *   Utiliza la **JetBrains Runtime (JBR)** por defecto provista por Android Studio Koala para compilar.

---

## 📝 Buenas Prácticas de Versionamiento con Git (Semana 3)

Recuerda que el uso de Git es obligatorio y progresivo. Sigue estas reglas al realizar tus commits:
*   Realiza commits descriptivos al finalizar cada componente (ej: `git commit -m "Diseño XML de la pantalla de Login con ConstraintLayout"`).
*   **Archivos a ignorar (.gitignore):** No subas archivos de compilación local (`.gradle/`, `build/`, `local.properties`, `.idea/`). Asegúrate de que estén registrados en tu archivo `.gitignore`.
