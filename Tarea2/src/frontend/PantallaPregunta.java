package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PantallaPregunta extends JFrame {
    private JPanel panelPrincipal;
    private JLabel enunciado;
    private JPanel opciones;
    private JButton anterior;
    private JButton siguiente;
    private JPanel botonera;

    private List<Pregunta> preguntas;
    private Evaluacion evaluacion;
    private int indiceActual = 0;

    public PantallaPregunta(List<Pregunta> preguntas, Evaluacion evaluacion) {
        this.preguntas = preguntas;
        this.evaluacion = evaluacion;

        setTitle("Responder Preguntas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);

        cargarPreguntaActual();

        anterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceActual > 0) {
                    guardarRespuestaSeleccionada();
                    indiceActual--;
                    cargarPreguntaActual();
                }
            }
        });

        siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRespuestaSeleccionada();
                if (indiceActual < preguntas.size() - 1) {
                    indiceActual++;
                    cargarPreguntaActual();
                } else {
                    // Finaliza la prueba
                    JOptionPane.showMessageDialog(PantallaPregunta.this, "¡Has respondido todas las preguntas!");

                    JFrame resumen = new JFrame("Resumen");
                    PantallaResumen pantallaResumen = new PantallaResumen(preguntas, evaluacion);
                    resumen.setContentPane(pantallaResumen.getPanelPrincipal());
                    resumen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    resumen.pack();
                    resumen.setLocationRelativeTo(null);
                    resumen.setVisible(true);
                    dispose();
                }
            }
        });
    }

    private void cargarPreguntaActual() {
        Pregunta pregunta = preguntas.get(indiceActual);
        enunciado.setText(pregunta.getEnunciado());

        opciones.removeAll();
        ButtonGroup grupo = new ButtonGroup();

        if (pregunta.getTipo() == Pregunta.Tipo.OPCION_MULTIPLE) {
            List<String> opcionesTexto = pregunta.getOpciones();
            for (int i = 0; i < opcionesTexto.size(); i++) {
                JRadioButton boton = new JRadioButton(opcionesTexto.get(i));
                char letra = (char) ('A' + i);
                boton.setActionCommand(String.valueOf(letra));
                grupo.add(boton);
                opciones.add(boton);

                // restaura la opcion seleccionada si ya existe
                String respuesta = evaluacion.getRespuestaUsuario(indiceActual);
                if (respuesta.equals(String.valueOf(letra))) {
                    boton.setSelected(true);
                }
            }
        } else if (pregunta.getTipo() == Pregunta.Tipo.VERDADERO_FALSO) {
            JRadioButton verdadero = new JRadioButton("Verdadero");
            verdadero.setActionCommand("true");
            JRadioButton falso = new JRadioButton("Falso");
            falso.setActionCommand("false");

            grupo.add(verdadero);
            grupo.add(falso);
            opciones.add(verdadero);
            opciones.add(falso);

            String respuesta = evaluacion.getRespuestaUsuario(indiceActual);
            if (respuesta.equalsIgnoreCase("true")) {
                verdadero.setSelected(true);
            } else if (respuesta.equalsIgnoreCase("false")) {
                falso.setSelected(true);
            }
        }

        opciones.revalidate();
        opciones.repaint();
        anterior.setEnabled(indiceActual != 0);
        siguiente.setText(indiceActual == preguntas.size() - 1 ? "Enviar respuestas" : "Siguiente");
    }

    private void guardarRespuestaSeleccionada() {
        for (Component comp : opciones.getComponents()) {
            if (comp instanceof JRadioButton) {
                JRadioButton radio = (JRadioButton) comp;
                if (radio.isSelected()) {
                    evaluacion.registrarRespuesta(indiceActual, radio.getActionCommand());
                    break;
                }
            }
        }
    }
}
