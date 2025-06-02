package frontend;

import backend.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class PantallaCarga extends JFrame {
    public JPanel panelPrincipal;
    public JLabel titulo;
    public JTextField ruta;
    public JButton buscar;
    public JButton cargar;

    private File archivoSeleccionado;
    private List<Pregunta> preguntas;

    public PantallaCarga() {
        preguntas = new ArrayList<>();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.panelPrincipal);
        this.pack();
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int opcion = fileChooser.showOpenDialog(panelPrincipal);

                if (opcion == JFileChooser.APPROVE_OPTION) {
                    archivoSeleccionado = fileChooser.getSelectedFile();
                    ruta.setText(archivoSeleccionado.getAbsolutePath());
                }
            }
        });

        cargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (archivoSeleccionado == null) {
                    JOptionPane.showMessageDialog(panelPrincipal, "Por favor selecciona un archivo primero.");
                    return;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(archivoSeleccionado))) {
                    String linea;
                    preguntas.clear();

                    while ((linea = reader.readLine()) != null) {
                        String[] partes = linea.split(";", -1); // -1 era para no ignorar los espacios vacíos
                        if (partes.length != 6) throw new IllegalArgumentException("Formato incorrecto: " + linea);

                        String enunciado = partes[0].trim();
                        Taxonomia nivel = Taxonomia.valueOf(partes[1].trim().toUpperCase());
                        Pregunta.Tipo tipo = Pregunta.Tipo.valueOf(partes[2].trim().toUpperCase());
                        List<String> opciones;
                        if (partes[3].isEmpty()) {
                            opciones = null;
                        } else {
                            opciones = Arrays.asList(partes[3].split(","));
                        }
                        String respuestaCorrecta = partes[4].trim();
                        int tiempo = Integer.parseInt(partes[5].trim());

                        Pregunta pregunta = new Pregunta(enunciado, nivel, tipo, opciones, respuestaCorrecta, tiempo);
                        preguntas.add(pregunta);
                    }

                    int tiempoTotal = 0;
                    for (Pregunta p : preguntas) {
                        tiempoTotal += p.getTiempoEstimado();
                    }

                    JOptionPane.showMessageDialog(panelPrincipal,
                            "Archivo cargado exitosamente con " + preguntas.size() + " preguntas.\n" +
                                    "Tiempo total estimado: " + tiempoTotal + " segundos.");


// Abrir PantallaPregunta
                    Evaluacion evaluacion = new Evaluacion(preguntas);
                    PantallaPregunta pantallaPregunta = new PantallaPregunta(preguntas, evaluacion);
                    pantallaPregunta.setVisible(true);
                    pantallaPregunta.setLocationRelativeTo(null); // Centra la ventana

// Cierra la ventana actual
                    SwingUtilities.getWindowAncestor(panelPrincipal).dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelPrincipal,
                            "Error al cargar el archivo: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
