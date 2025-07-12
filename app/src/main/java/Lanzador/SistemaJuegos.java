package Lanzador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SistemaJuegos extends JPanel implements ActionListener {
    private Juego juegoActual;
    private Thread hiloJuego;
    private JFrame ventana;

    public SistemaJuegos(){
        ventana = new JFrame("Lanzador de Juegos");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 400);
        ventana.setLocationRelativeTo(null);
        mostrarMenuPrincipal();
        ventana.setVisible(true);
    }

    private void mostrarMenuPrincipal(){
        // Panel del fondo personalizado que escala la imagen
        ImageIcon fondo = new ImageIcon(getClass().getResource("/ImagenesLanzador/FondoLanzador.jpg"));
        JLabel labelFondo = new JLabel(fondo);
        labelFondo.setLayout(new BorderLayout());
        ventana.setContentPane(labelFondo);

        // Panel para los botones en la parte inferior
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
        panelBotones.setOpaque(false); // Para que el fondo sea transparente

        for(String nombre: new String[]{"JuegoPong", "Lemmings"}){
            JButton boton = new JButton(nombre);
            boton.setFont(new Font("Arial", Font.BOLD, 18));
            boton.setPreferredSize(new Dimension(180, 50));
            boton.addActionListener(this);
            panelBotones.add(boton);
        }

        labelFondo.add(panelBotones, BorderLayout.SOUTH); // Agrega los botones abajo

        ventana.revalidate();
        ventana.repaint();
    }

    private void mostrarMenuJuego(String juego) {
        // Elegimos la imagen de fondo según el juego
        String rutaFondo = juego.equals("JuegoPong")
            ? "/ImagenesPong/PortadaPong.png"
            : "/Imagenes_Lemmings/PortadaLemmings.jpg";

        ImageIcon iconoFondo = new ImageIcon(getClass().getResource(rutaFondo));
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                g.drawImage(iconoFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new BoxLayout(panelFondo, BoxLayout.Y_AXIS));
        panelFondo.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        ventana.setContentPane(panelFondo);

        // Título
        JLabel titulo = new JLabel(juego);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.BLACK);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado horizontalmente

        // Botón Jugar
        JButton btnJugar = new JButton("Jugar");
        btnJugar.setFont(new Font("Arial", Font.PLAIN, 18));
        btnJugar.setMaximumSize(new Dimension(200, 40));
        btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado horizontalmente
        btnJugar.addActionListener(e -> iniciarJuego(juego));

        // Botón Ver Ranking
        JButton btnRanking = new JButton("Ver Ranking");
        btnRanking.setFont(new Font("Arial", Font.PLAIN, 18));
        btnRanking.setMaximumSize(new Dimension(200, 40));
        btnRanking.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado horizontalmente
        btnRanking.addActionListener(e ->JOptionPane.showMessageDialog(ventana,
        "Aquí iría el ranking de " + juego,
        "Ranking", JOptionPane.INFORMATION_MESSAGE)); // Mostrar mensaje de ranking

        // Botón Volver
        JButton btnVolver = new JButton("← Volver");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 16));
        btnVolver.setMaximumSize(new Dimension(120, 30)); // Tamaño máximo del botón
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> mostrarMenuPrincipal()); // Volver al menú principal

        // Armado del layout
        panelFondo.add(titulo);
        panelFondo.add(Box.createRigidArea(new Dimension(0, 30))); // Espacio entre título y botones
        panelFondo.add(btnJugar);
        panelFondo.add(Box.createRigidArea(new Dimension(0, 20)));  // Espacio entre botones
        panelFondo.add(btnRanking);
        panelFondo.add(Box.createVerticalGlue()); // Para empujar los botones hacia arriba
        panelFondo.add(btnVolver);

        ventana.revalidate();
        ventana.repaint();
    }

    private void iniciarJuego(String juego) {

        if (hiloJuego != null && hiloJuego.isAlive()) {
            // aquí podrías interrumpir el hilo, etc.
        }
        // Igual a tu lógica anterior: lanza el juego en un hilo
        switch (juego) {
            case "JuegoPong":
                juegoActual = new pong.Pong();
                break;
            case "Lemmings":
                juegoActual = new JuegoLemmings.Lemmings();
                break;
        }
        hiloJuego = new Thread(() -> juegoActual.run(1.0/60.0));
        hiloJuego.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Cuando clickeás “JuegoPong” o “Lemmings”:
        mostrarMenuJuego(e.getActionCommand());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaJuegos::new);
    }

}
