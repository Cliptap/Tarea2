package backend;

import java.util.List;

public class Pregunta {
    public enum Tipo {
        OPCION_MULTIPLE,
        VERDADERO_FALSO
    }

    private String enunciado;
    private Taxonomia nivelTaxonomia;
    private Tipo tipo;
    private String respuestaUsuario = "";

    public void setRespuestaUsuario(String respuesta) {
        this.respuestaUsuario = respuesta;
    }

    public String getRespuestaUsuario() {
        return respuestaUsuario;
    }
    private List<String> opciones;  //para OM
    private String respuestaCorrecta; // para OM y VF

    private int tiempoEstimado; // en segundos

    public Pregunta(String enunciado, Taxonomia nivel, Tipo tipo,
                    List<String> opciones, String respuestaCorrecta, int tiempoEstimado) {
        this.enunciado = enunciado;
        this.nivelTaxonomia = nivel;
        this.tipo = tipo;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta.toLowerCase(); // uniformizar
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public Taxonomia getNivelTaxonomia() {
        return nivelTaxonomia;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public boolean verificarRespuesta(String respuestaUsuario) {
        if (respuestaUsuario == null) return false;
        String resp = respuestaUsuario.trim().toLowerCase();

        if (tipo == Tipo.OPCION_MULTIPLE) {
            return resp.equals(respuestaCorrecta);
        } else if (tipo == Tipo.VERDADERO_FALSO) {
            if (resp.equals("v")) resp = "true";
            else if (resp.equals("f")) resp = "false";
            return resp.equals(respuestaCorrecta);
        }

        return false;
    }
}
