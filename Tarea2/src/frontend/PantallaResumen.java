package frontend;

import backend.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PantallaResumen {
    private JPanel panelPrincipal;
    private JLabel enunciado;
    private JPanel botonera;
    private JButton revisar;
    private JButton salir;
    private JPanel campo;
    private JLabel recordar;
    private JLabel entender;
    private JLabel aplicar;
    private JLabel analizar;
    private JLabel evaluar;
    private JLabel crear;

    public PantallaResumen(List<Pregunta> preguntas, Evaluacion evaluacion) {
        int correctas = 0;
        for (Pregunta p : preguntas) {
            String respuesta = evaluacion.getRespuestaUsuario(preguntas.indexOf(p));
            System.out.println(p.getEnunciado() + " - Nivel: " + p.getNivelTaxonomia());
            System.out.println("Respuesta usuario: " + respuesta);

            if (p.verificarRespuesta(respuesta)) {
                correctas++;
            }
        }

        String resumenTexto = String.format(
                "Respondiste correctamente %d de %d preguntas. ¡Bien hecho!",
                correctas, preguntas.size());

        enunciado.setText(resumenTexto);

        recordar.setText("RECORDAR: " + String.format("%.2f", evaluacion.porcentajeCorrectasPorNivel(Taxonomia.RECORDAR)) + "%");
        entender.setText("ENTENDER: " + String.format("%.2f", evaluacion.porcentajeCorrectasPorNivel(Taxonomia.ENTENDER)) + "%");
        aplicar.setText("APLICAR: " + String.format("%.2f", evaluacion.porcentajeCorrectasPorNivel(Taxonomia.APLICAR)) + "%");
        analizar.setText("ANALIZAR: " + String.format("%.2f", evaluacion.porcentajeCorrectasPorNivel(Taxonomia.ANALIZAR)) + "%");
        evaluar.setText("EVALUAR: " + String.format("%.2f", evaluacion.porcentajeCorrectasPorNivel(Taxonomia.EVALUAR)) + "%");
        crear.setText("CREAR: " + String.format("%.2f", evaluacion.porcentajeCorrectasPorNivel(Taxonomia.CREAR)) + "%");

        revisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame revisarFrame = new JFrame("Revisión de respuestas");
                revisarFrame.setContentPane(new PantallaRevisar(preguntas, evaluacion).getPanelPrincipal());
                revisarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                revisarFrame.pack();
                revisarFrame.setLocationRelativeTo(null);
                revisarFrame.setVisible(true);
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
