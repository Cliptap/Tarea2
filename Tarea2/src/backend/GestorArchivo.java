package backend;

import backend.Pregunta;
import backend.Taxonomia;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivo {
    private List<Pregunta> preguntas;

    public GestorArchivo() {
        preguntas = new ArrayList<>();
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public Evaluacion cargarEvaluacion(String rutaArchivo) throws IOException {
        preguntas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String enunciado;
            while ((enunciado = br.readLine()) != null) {
                if (enunciado.trim().isEmpty()) continue;

                String nivelStr = br.readLine();
                String tipoStr = br.readLine();
                String respuestaCorrecta = br.readLine();
                String tiempoStr = br.readLine();

                Taxonomia nivel = Taxonomia.valueOf(nivelStr.trim());
                Pregunta.Tipo tipo;
                if (tipoStr.trim().equalsIgnoreCase("OM")) {
                    tipo = Pregunta.Tipo.OPCION_MULTIPLE;
                } else {
                    tipo = Pregunta.Tipo.VERDADERO_FALSO;
                }
                int tiempoEstimado = Integer.parseInt(tiempoStr.trim());

                List<String> opciones = new ArrayList<>();

                if (tipo == Pregunta.Tipo.OPCION_MULTIPLE) {
                    String opcion;
                    while ((opcion = br.readLine()) != null && !opcion.trim().isEmpty()) {
                        opciones.add(opcion);
                    }
                }

                Pregunta p = new Pregunta(enunciado, nivel, tipo, opciones, respuestaCorrecta, tiempoEstimado);
                preguntas.add(p);
            }
        }
        return new Evaluacion(preguntas);
    }

    //Guarda las preguntas en un archivo de texto con el mismo formato de lectura
    public void guardarPreguntasEnArchivo(String rutaArchivo) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (Pregunta p : preguntas) {
                pw.println(p.getEnunciado());
                pw.println(p.getNivelTaxonomia().name());
                if (p.getTipo() == Pregunta.Tipo.OPCION_MULTIPLE) {
                    pw.println("OM");
                } else {
                    pw.println("VF");
                }
                pw.println(p.getRespuestaCorrecta());
                pw.println(p.getTiempoEstimado());
                if (p.getTipo() == Pregunta.Tipo.OPCION_MULTIPLE) {
                    for (String opcion : p.getOpciones()) {
                        pw.println(opcion);
                    }
                    pw.println(); // línea vacía para separar preguntas
                } else {
                    pw.println(); // línea vacía para separar preguntas
                }
            }
        }
    }
}
