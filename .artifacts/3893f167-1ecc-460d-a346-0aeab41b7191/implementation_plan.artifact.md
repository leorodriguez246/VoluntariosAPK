# Modernización y Accesibilidad de Vistas

Este plan detalla los cambios estéticos y de diseño para las tres pantallas actuales (`MainActivity`, `LoginVoluntarioActivity`, y `RegistroVoluntarioActivity`), enfocándose en la modernidad (Material 3) y la accesibilidad para adultos mayores.

## User Review Required

> [!IMPORTANT]
> Se utilizarán componentes de **Material Design 3** (como `TextInputLayout` y `MaterialButton`) para mejorar la visibilidad y la experiencia táctil. Esto requiere que el proyecto tenga configurado un tema de Material Components (ya está presente en `themes.xml`).

## Proposed Changes

### Estilos y Recursos

#### [MODIFY] [colors.xml](file:///Users/lrodriguez/AndroidStudioProjects/Voluntarios/app/src/main/res/values/colors.xml)
Añadir una paleta de colores moderna con alto contraste:
- `primary_blue`: #1B365D (Azul institucional)
- `secondary_green`: #2E7D32 (Verde accesible)
- `background_light`: #F8F9FA (Gris muy claro para fondos)
- `text_primary`: #212121 (Casi negro para máxima legibilidad)

---

### Pantallas

#### [MODIFY] [activity_main.xml](file:///Users/lrodriguez/AndroidStudioProjects/Voluntarios/app/src/main/res/layout/activity_main.xml)
- **Cabecera**: Aumentar el tamaño de fuente y usar un color de contraste alto.
- **Botones**: Transformar los botones en elementos más grandes, con iconos claros y esquinas redondeadas. Se usará `MaterialButton` para un mejor feedback visual.
- **Espaciado**: Optimizar los márgenes para evitar que los elementos se sientan amontonados, facilitando la interacción táctil.

#### [MODIFY] [activity_login_voluntario.xml](file:///Users/lrodriguez/AndroidStudioProjects/Voluntarios/app/src/main/res/layout/activity_login_voluntario.xml)
- **Contenedores de Texto**: Implementar `TextInputLayout` con estilo "OutlinedBox". Esto hace que los campos de entrada sean mucho más visibles para personas con visión reducida.
- **Jerarquía Visual**: Centrar el formulario en una "tarjeta" visual o usar un diseño más limpio con enfoque en los campos de entrada.
- **Botones de Navegación**: Mejorar los enlaces de "Registrarse" y "Volver" para que parezcan botones secundarios o tengan un área de clic más grande.

#### [MODIFY] [activity_registro_voluntario.xml](file:///Users/lrodriguez/AndroidStudioProjects/Voluntarios/app/src/main/res/layout/activity_registro_voluntario.xml)
- **Formulario**: Al igual que el login, usar `TextInputLayout` para todos los campos (Nombre, Email, Teléfono, Contraseña).
- **Accesibilidad**: Asegurar que los "hints" (pistas) no desaparezcan al escribir o sean muy claros.
- **Validación Visual**: Preparar el diseño para mostrar errores de forma clara debajo de cada campo.

---

## Sugerencias Adicionales (Pendientes de Confirmación)

Estas son mejoras que van más allá de lo puramente estético y que te sugiero considerar para una siguiente etapa:

1.  **Iconografía**: Integrar una librería de iconos (como FontAwesome o Material Icons) para acompañar los textos, lo cual ayuda mucho a la comprensión rápida por parte de adultos mayores.
2.  **Modo de Lectura**: Implementar un botón para aumentar el tamaño de fuente dinámicamente en toda la app.
3.  **Confirmaciones por Voz**: Añadir soporte básico para que la app "lea" las opciones principales al pasar el dedo sobre ellas (TalkBack optimization).
4.  **Tematización Completa**: Configurar el archivo `themes.xml` para que todos los nuevos componentes hereden los colores automáticamente, facilitando futuros cambios globales.

## Verification Plan

### Manual Verification
- Previsualizar los layouts en el editor de Android Studio para diferentes tamaños de pantalla (especialmente pantallas grandes y pequeñas).
- Verificar que el contraste de color cumpla con las guías de accesibilidad (WCAG AA).
- Probar la navegación básica entre las 3 pantallas para asegurar que los nuevos botones funcionen correctamente.
