package Lanzador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

import JuegoLemmings.ConfiguracionLemmings;
import pong.ConfiguracionPong;
import util.Sonido;

public class SistemaJuegos extends JPanel implements ActionListener {
    private Juego juegoActual;
    private final ExecutorService ejecutorJuegos = Executors.newSingleThreadExecutor();
    //El hilo "ejecutorJuegos", sirve para ejecutar los juegos que hay sin bloquear la GUI y es de un solo hilo 
    private JFrame ventana;
    //Scheduler sirve para verificar si un archivo fue modificado o no cada cierto tiempo 
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    Sonido sonido = new Sonido();
    ConfiguracionLemmings configLemmings = new ConfiguracionLemmings();
    ConfiguracionPong configPong = new ConfiguracionPong();

    public SistemaJuegos() {
        ventana = new JFrame("Lanzador de Juegos");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 400);
        ventana.setLocationRelativeTo(null);
        mostrarMenuPrincipal();
        ventana.setVisible(true);

        Path archivo = Paths.get("ruta/a/tu/archivo.txt");
        long[] ultimaModif = { archivo.toFile().lastModified() };

        scheduler.scheduleAtFixedRate(() -> {
            long modif = archivo.toFile().lastModified();
            if (modif != ultimaModif[0]) {
                ultimaModif[0] = modif;
                SwingUtilities.invokeLater(() -> {
                    ventana.setVisible(true);
                    JOptionPane.showMessageDialog(ventana, "¡El archivo fue modificado!");
                });
            }
        }, 2, 2, TimeUnit.SECONDS);

        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                cerrarYSalir();
            }
        });
    }

    private void mostrarMenuPrincipal() {
        // Panel del fondo personalizado que escala la imagen
        ImageIcon fondo = new ImageIcon(getClass().getResource("/ImagenesLanzador/FondoLanzador.jpg"));
        JLabel labelFondo = new JLabel(fondo);
        labelFondo.setLayout(new BorderLayout());
        ventana.setContentPane(labelFondo);

        // Panel para los botones en la parte inferior
        JPanel panelCentral = new JPanel();
        panelCentral.setOpaque(false);
        panelCentral.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 30)); // Espacio entre tarjetas

        // Tarjetas para cada juego
        panelCentral.add(crearTarjetaJuego("Pong2D", "/ImagenesLanzador/PortadaPong.png"));
        panelCentral.add(crearTarjetaJuego("Lemmings", "/ImagenesLanzador/PortadaLemmings2.png"));

        labelFondo.add(panelCentral, BorderLayout.CENTER);

        ventana.revalidate();
        ventana.repaint();
    }

    private JPanel crearTarjetaJuego(String nombreJuego, String rutaImagen) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setOpaque(false);
        tarjeta.setPreferredSize(new Dimension(200, 300));

        // Título (opcional)
        JLabel titulo = new JLabel(nombreJuego);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Imagen del juego
        ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen));
        Image imagenEscalada = icono.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(imagenEscalada));
        labelImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón “Jugar”
        JButton btnJugar = new JButton("Jugar");
        btnJugar.setFont(new Font("Arial", Font.BOLD, 16));
        btnJugar.setMaximumSize(new Dimension(180, 35));
        btnJugar.setPreferredSize(new Dimension(180, 35));
        btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnJugar.setFocusPainted(false);
        btnJugar.addActionListener(e -> mostrarMenuJuego(nombreJuego));

        tarjeta.add(titulo);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 10)));
        tarjeta.add(labelImagen);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 5)));
        tarjeta.add(btnJugar);

        return tarjeta;
    }

    private void mostrarMenuJuego(String juego) {
        // Elegimos la imagen de fondo según el juego
        String rutaFondo = juego.equals("Pong2D")
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
        btnRanking.addActionListener(e -> {
            List<JuegoLemmings.Jugador> top = leerRankingDesdeArchivo();

            if (top == null || top.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "Aún no hay puntuaciones guardadas.", "Ranking Lemmings", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("🏆 RANKING DESDE ARCHIVO 🏆\n\n");
                int pos = 1;
                for (JuegoLemmings.Jugador j : top) {
                    long min = j.getTiempoSegundos() / 60;
                    long seg = j.getTiempoSegundos() % 60;
                    sb.append(String.format("%d. %s – %02d:%02d – %d salvados\n",
                            pos++, j.getNombre(), min, seg, j.getLemmingsSalvados()));
                }

                JOptionPane.showMessageDialog(ventana, sb.toString(), "Ranking Lemmings", JOptionPane.INFORMATION_MESSAGE);
            }
        });

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
        panelFondo.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre botones
        panelFondo.add(btnRanking);
        panelFondo.add(Box.createVerticalGlue()); // Para empujar los botones hacia arriba
        panelFondo.add(btnVolver);

        ventana.revalidate();
        ventana.repaint();
    }

    private void iniciarJuego(String juego) {
        switch (juego) {
            case "Pong2D":
                juegoActual = new pong.Pong(sonido,configPong);
                break;
            case "Lemmings":
                juegoActual = new JuegoLemmings.Lemmings(sonido,configLemmings);
                break;
        }
        ventana.setVisible(false);

        //Esta seccion se encarga de lanzar el juego en un hilo separado
        ejecutorJuegos.submit(() -> {
            try {
                juegoActual.run(1.0 / 60.0);
            } catch (Exception e) {
                e.printStackTrace();
                //Muestra un mensaje de error en una ventana, mostrandolo en un hilo correcto
                //Si no se usa invokeLater, y la ejecucuion esta en un hilo que no corresponde, puede fallar la GUI 
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(ventana,
                        "Hubo un error al ejecutar el juego:\n" + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                });
            } finally {
                //Toma una tarea runnable y la pone en una cola de eventos
                //Cuando el hilo de la GUI esta libre, lo ejecuta
                SwingUtilities.invokeLater(() -> {
                    mostrarMenuPrincipal();
                    ventana.setVisible(true);
                });
            }
        });
    }

    //Cuando el usuario cierra la ventana, los hilos deberian de liberarse
    private void cerrarYSalir() {
        if (!scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
        ejecutorJuegos.shutdownNow();
        System.exit(0); // Finaliza todo
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Cuando clickeás “JuegoPong” o “Lemmings”:
        mostrarMenuJuego(e.getActionCommand());
    }

    @SuppressWarnings("unchecked")
        private List<JuegoLemmings.Jugador> leerRankingDesdeArchivo() {
            List<JuegoLemmings.Jugador> jugadores = new ArrayList<>();

            try (ObjectInputStream ois = new ObjectInputStream(new java.io.FileInputStream("ranking.dat"))) {
                jugadores = (List<JuegoLemmings.Jugador>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(ventana, "Error al leer el archivo de ranking.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            return jugadores;
        }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaJuegos::new);
    }

}
