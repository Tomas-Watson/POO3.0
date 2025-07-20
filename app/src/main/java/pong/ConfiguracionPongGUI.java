package pong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConfiguracionPongGUI extends JFrame {
    
    private final Map<String, JButton> botonesTecla = new HashMap<>();
    private final Map<String, Integer> teclasTemp = new HashMap<>();
    private final JLabel infoLabel = new JLabel("Haz clic en una acción y luego presiona una tecla");

    private final Pong juego; 

    private boolean cerrada = false; 

    public ConfiguracionPongGUI(Pong juego) {
        this.juego = juego;
        setTitle("Configuración de Teclas - Pong");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // NUEVO: Layout principal vertical
        setLayout(new BorderLayout());

        // Panel para los controles
        JPanel panelControles = new JPanel(new GridLayout(5, 2, 10, 10));
        ConfiguracionPong config = ConfiguracionPong.get();
        teclasTemp.putAll(config.teclas);

        addLabelYBoton(panelControles, "Jugador 1 Subir", "J1_UP");
        addLabelYBoton(panelControles, "Jugador 1 Bajar", "J1_DOWN");
        addLabelYBoton(panelControles, "Jugador 2 Subir", "J2_UP");
        addLabelYBoton(panelControles, "Jugador 2 Bajar", "J2_DOWN");

        panelControles.add(infoLabel);
        infoLabel.setForeground(Color.BLUE);

        add(panelControles, BorderLayout.CENTER);

        // Panel para botones al pie
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton guardar = new JButton("Guardar");
        guardar.addActionListener(e -> {
            config.teclas.putAll(teclasTemp);
            JOptionPane.showMessageDialog(this, "Teclas guardadas correctamente");
            cerrarVentana();
        });

        JButton volver = new JButton("Volver al Juego");
        volver.addActionListener(e -> cerrarVentana());

        panelBotones.add(volver);
        panelBotones.add(guardar);

        add(panelBotones, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cerrarVentana();
            }
        });

        setVisible(true);
    }

    private void addLabelYBoton(JPanel panel, String texto, String clave) {
        panel.add(new JLabel(texto + ":"));

        JButton boton = new JButton(KeyEvent.getKeyText(teclasTemp.get(clave)));
        boton.addActionListener(e -> esperarTecla(boton, clave));
        botonesTecla.put(clave, boton);
        panel.add(boton);
    }

    private void cerrarVentana() {
        if (!cerrada) {
            cerrada = true;
            setVisible(false);   // Oculta la ventana
            dispose();           // Libera recursos
            juego.configuracionCerrada(); // Informa al juego que se cerró
        }
    }

    private void esperarTecla(JButton boton, String clave) {
        infoLabel.setText("Presiona una tecla...");
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent ke) {
                if (ke.getID() == KeyEvent.KEY_PRESSED) {
                    int nuevaTecla = ke.getKeyCode();
                    teclasTemp.put(clave, nuevaTecla);
                    boton.setText(KeyEvent.getKeyText(nuevaTecla));
                    infoLabel.setText("Asignada: " + KeyEvent.getKeyText(nuevaTecla));
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
                    return true;
                }
                return false;
            }
        });
    }
}