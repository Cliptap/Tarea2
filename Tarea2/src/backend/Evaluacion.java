package backend;

import backend.*;

import java.util.List;

public class Evaluacion {
    private List<Pregunta> preguntas;
    private String[] respuestasUsuario;

    public Evaluacion(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
        this.respuestasUsuario = new String[preguntas.size()];
        // inicializacion con strings vacíos
        for (int i = 0; i < respuestasUsuario.length; i++) {
            respuestasUsuario[i] = "";
        }
    }

    public int totalPreguntas() {
        return preguntas.size();
    }

    public Pregunta getPregunta(int index) {
        return preguntas.get(index);
    }

    public void registrarRespuesta(int index, String respuesta) {
        if (index >= 0 && index < respuestasUsuario.length) {
            respuestasUsuario[index] = respuesta;
        }
    }

    public String getRespuestaUsuario(int index) {
        if (index >= 0 && index < respuestasUsuario.length) {
            return respuestasUsuario[index];
        }
        return "";
    }

    public int getTiempoTotal() {
        int suma = 0;
        for (Pregunta p : preguntas) {
            suma += p.getTiempoEstimado();
        }
        return suma;
    }

    public double porcentajeCorrectasPorNivel(Taxonomia nivel) {
        int totalNivel = 0;
        int correctasNivel = 0;

        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta p = preguntas.get(i);
            if (p.getNivelTaxonomia() == nivel) {
                totalNivel++;
                if (p.verificarRespuesta(respuestasUsuario[i])) {
                    correctasNivel++;
                }
            }
        }
        if (totalNivel == 0) return 0;
        return (correctasNivel * 100.0) / totalNivel;
    }

    public double porcentajeCorrectasPorTipo(String tipoClase) {
        int totalTipo = 0;
        int correctasTipo = 0;

        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta p = preguntas.get(i);
            if (p.getClass().getSimpleName().equals(tipoClase)) {
                totalTipo++;
                if (p.verificarRespuesta(respuestasUsuario[i])) {
                    correctasTipo++;
                }
            }
        }
        if (totalTipo == 0) return 0;
        return (correctasTipo * 100.0) / totalTipo;
    }
}
