Curso: Paradigmas de Programación
Trabajo: Tarea 2 – Sistema de Evaluación basado en la Taxonomía de Bloom
Estudiantes: Andrés Tapia y Daniel Cortés
Fecha: 02/06/2025

---

LEAME - Instrucciones de Ejecución y Alcances

Este sistema permite administrar y aplicar una prueba cuyos ítems están categorizados según la Taxonomía de Bloom, mediante una interfaz desarrollada en Java Swing.

Requisitos
- IntelliJ IDEA (versión 2024 o superior)
- JDK 23 correctamente configurado
- Proyecto abierto como Proyecto IntelliJ existente

Estructura del Proyecto
- "/src/backend/": lógica de aplicación (Evaluacion, Pregunta, etc.)
- "/src/frontend/": interfaz gráfica construida con Swing
- ".idea/": configuración de IntelliJ necesaria
- ".form": archivos del Swing UI Designer.

Instrucciones de ejecución
1. Abrir el proyecto en IntelliJ seleccionando el directorio completo (incluye ".idea").
2. Ejecutar la clase "Main.java".
3. Al iniciar, se abrirá la ventana principal ("PantallaCarga").
4. Presionar "Buscar" para seleccionar el archivo "preguntas.txt" dentro de la carpeta principal "Tarea2"
5. Luego presionar "Cargar".
6. Comenzar la prueba desde la nueva ventana.

Especificación del archivo de ítems (preguntas.txt)
Cada línea del archivo representa una pregunta y sigue este formato exacto:

ENUNCIADO;NIVEL_BLOOM;TIPO_PREGUNTA;OPCIONES_SEPARADAS_POR_COMA;RESPUESTA_CORRECTA;TIEMPO_ESTIMADO

Campos:
- ENUNCIADO: Texto de la pregunta.
- NIVEL_BLOOM: Uno de los siguientes (en mayúscula): RECORDAR, ENTENDER, APLICAR, ANALIZAR, EVALUAR, CREAR.
- TIPO_PREGUNTA: OPCION_MULTIPLE o VERDADERO_FALSO.
- OPCIONES: Solo aplica para OPCION_MULTIPLE, separadas por coma. Si es VERDADERO_FALSO, dejar vacío (;;).
- RESPUESTA_CORRECTA:
    - Letra correspondiente (A, B, ...) para opción múltiple.
    - "Verdadero" o "Falso" para verdadero/falso.
- TIEMPO_ESTIMADO: número en segundos.
