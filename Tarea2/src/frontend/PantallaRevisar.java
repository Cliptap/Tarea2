package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PantallaRevisar extends JFrame {
    private JPanel panelPrincipal;
    private JLabel enunciado;
    private JPanel opciones;
    private JPanel botonera;
    private JButton anterior;
    private JButton siguiente;
    private JLabel resultado;

    private List<Pregunta> preguntas;
    private Evaluacion evaluacion;
    private int indiceActual = 0;

    public PantallaRevisar(List<Pregunta> preguntas, Evaluacion evaluacion) {
        this.preguntas = preguntas;
        this.evaluacion = evaluacion;

        setTitle("Revisión de respuestas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        cargarPregunta();

        anterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceActual > 0) {
                    indiceActual--;
                    cargarPregunta();
                }
            }
        });

        siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceActual < preguntas.size() - 1) {
                    indiceActual++;
                    cargarPregunta();
                }
            }
        });
    }

    private void cargarPregunta() {
        Pregunta pregunta = preguntas.get(indiceActual);
        enunciado.setText("Pregunta " + (indiceActual + 1) + ": " + pregunta.getEnunciado());
        opciones.removeAll();

        String respuestaUsuario = evaluacion.getRespuestaUsuario(indiceActual);
        String respuestaCorrecta = pregunta.getRespuestaCorrecta();

        if (pregunta.getTipo() == Pregunta.Tipo.OPCION_MULTIPLE) {
            List<String> ops = pregunta.getOpciones();
            for (int i = 0; i < ops.size(); i++) {
                char letra = (char) ('A' + i);
                String texto = letra + ": " + ops.get(i);

                JLabel label = new JLabel(texto);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

                if (String.valueOf(letra).equalsIgnoreCase(respuestaCorrecta)) {
                    label.setForeground(Color.GREEN);
                }

                if (String.valueOf(letra).equalsIgnoreCase(respuestaUsuario) &&
                        !respuestaUsuario.equalsIgnoreCase(respuestaCorrecta)) {
                    label.setForeground(Color.RED);
                }

                opciones.add(label);
            }

        } else if (pregunta.getTipo() == Pregunta.Tipo.VERDADERO_FALSO) {
            JLabel verdadero = new JLabel("Verdadero");
            verdadero.setOpaque(true);
            verdadero.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

            JLabel falso = new JLabel("Falso");
            falso.setOpaque(true);
            falso.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

            if ("true".equalsIgnoreCase(respuestaCorrecta)) {
                verdadero.setForeground(Color.GREEN);
            } else {
                falso.setForeground(Color.GREEN);
            }

            if ("true".equalsIgnoreCase(respuestaUsuario) &&
                    !"true".equalsIgnoreCase(respuestaCorrecta)) {
                verdadero.setForeground(Color.RED);
            } else if ("false".equalsIgnoreCase(respuestaUsuario) &&
                    !"false".equalsIgnoreCase(respuestaCorrecta)) {
                falso.setForeground(Color.RED);
            }

            opciones.add(verdadero);
            opciones.add(falso);
        }

        if (respuestaUsuario.equalsIgnoreCase(respuestaCorrecta)) {
            resultado.setText(" Respuesta Correcta");
        } else {
            resultado.setText(" Respuesta Incorrecta");
        }

        opciones.revalidate();
        opciones.repaint();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
