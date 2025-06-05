package poo.lemmings;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;
import com.entropyinteractive.Mouse;

public class Lemmings extends JGame {

    static final int originalTileSize = 16;
    static final int escala = 3;

    static final int tileSize = originalTileSize * escala;
    private static final int maxColumnas = 16;
    private static final int maxFilas = 12;
    private static final int ANCHO_PANTALLA = tileSize * maxColumnas;
    private static final int ALTO_PANTALLA = tileSize * maxFilas;

    private boolean finJuego = false;
    private boolean enPausa = false;
    private boolean pPresionado = false;

    private int PersonajeX = 100;
    private int PersonajeY = 100;
    private int velocidad = 4;

    private Pixel pixeles[];
    private Terreno terreno;
    private Entrada entrada;
    private Salida salida;
    private Object contador;
    private List<Lemming> listaLemmings;

    //Variables para los botones de habilidades
    private static final int ICON_COUNT = 11; // Ajusta este número según cuántos iconos tengas
    private BufferedImage[] iconosHabilidad;     // para guardar cada icono recortado
    private Rectangle[]    botonesHabilidad;   // para saber en qué rectángulo está cada botón
    private int habilidadActual = -1;

    public Lemmings() {
        super("Lemmings", ANCHO_PANTALLA, ALTO_PANTALLA);
    }

    @Override
    public void gameDraw(Graphics2D g) {
        // Seteo el color del fondo
        g.setColor(Color.black);
        g.fillRect(0, 0, ANCHO_PANTALLA, ALTO_PANTALLA);

        // Dibujo el terreno
        if (terreno != null) {
            terreno.draw(g);
        }

        // Dibujo la Entrada
        if (entrada != null) {
            entrada.draw(g);
        }

        // Dibujo la Salida
        if (salida != null) {
            salida.draw(g);
        }

        if (iconosHabilidad != null) {
            for (int i = 0; i < ICON_COUNT; i++) {
                BufferedImage icon = iconosHabilidad[i];
                Rectangle r = botonesHabilidad[i];
                g.drawImage(icon, r.x, r.y, null);

                // Si esta es la habilidad “seleccionada”, dibujamos un borde amarillo
                if (i == habilidadActual) {
                    g.setColor(Color.YELLOW);
                    g.drawRect(r.x, r.y, r.width, r.height);
                }
            }
        }
    }

    @Override
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    @Override
    public void gameStartup() {
        int colEntrada = 2;
        int filaEntrada = 6;

        int colSalida = 14;
        int filaSalida = 10;

        Log.info(getClass().getSimpleName(), "Ejecutando el juego");

        // Cargo el terreno
        pixeles = new Pixel[3];
        terreno = new Terreno(pixeles);
        terreno.getImg();
        terreno.CargarTerreno("/Imagenes_Lemmings/nivel1.txt");

        // Cargo la entrada
        entrada = new Entrada();
        entrada.setPosition(colEntrada * tileSize, filaEntrada * tileSize);

        // Cargo la salida
        salida = new Salida();
        salida.setPosition(colSalida * tileSize, filaSalida * tileSize);

        // Inicializo el contador
        contador = new Contador(10, 10, 120);
        cargarHabilidadBotones();
    }

    @Override
    public void gameUpdate(double arg0) {
        
        if (!finJuego) {
            Mouse mouse = this.getMouse();
            Keyboard keyboard = this.getKeyboard();

            // Pausar/reanudar el juego con 'P'
            if (keyboard.isKeyPressed(KeyEvent.VK_P)) {
                if (!pPresionado) {
                    enPausa = !enPausa;
                    pPresionado = true;
                }
            } else {
                pPresionado = false;
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                System.exit(0);
            }
        }
    }

    private void controlarPausa() {
        Keyboard teclado = this.getKeyboard();
        if (teclado.isKeyPressed(KeyEvent.VK_P)) {
            if (!pPresionado) {
                enPausa    = !enPausa;
                pPresionado = true;
            }
        } else {
            pPresionado = false;
        }
    }

    private void finalizarNivelPorTiempo() {
        // Cuando expira el tiempo, todos los lemmings restantes se consideran perdidos:
        for (Lemming lem : listaLemmings) {
            Contador contador.incrementarPerdidos();
        }
        listaLemmings.clear();
        // Aquí podrías mostrar pantalla de “Game Over” o pasar al siguiente nivel
        Log.info("¡Tiempo agotado! Lemmings perdidos: " + contador.getContadorPerdidos());
    }

    private void cargarHabilidadBotones() {
        try {
            // 1) Cargo el PNG 
            BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream("/imagenes/Lemming/BOTONES.png"));

            int fullWidth  = sheet.getWidth();   // ej: 365 px
            int fullHeight = sheet.getHeight();  // ej: 49 px

            // 2) ancho “aprox” de cada icono, todos caben en fila
            int iconWidth  = fullWidth / ICON_COUNT; // ej: 365/11 ≈ 33 px
            int iconHeight = fullHeight;             // 49 px

            iconosHabilidad   = new BufferedImage[ICON_COUNT];
            botonesHabilidad = new Rectangle[ICON_COUNT];

            // 3) iconosHabilida[i]
            // CHAT: Si el último icono “sobra” un pixel por división imperfecta, 
                // podrías usar: 
                // int w = (i == ICON_COUNT - 1) ? (fullWidth - x) : iconWidth;
                // Pero aquí simplificamos usando iconWidth siempre:
            for (int i = 0; i < ICON_COUNT; i++) {
                int x = i * iconWidth;
                int w = iconWidth;
                iconosHabilidad[i] = sheet.getSubimage(x, 0, w, iconHeight);

                // 4) Definir el Rectangle donde dibujaremos este icono
                int margenInferior = 10;
                int spacingH= 5;
                int startX = 10 + i * (iconWidth + spacingH);
                int startY = ALTO_PANTALLA - iconHeight - margenInferior;

                botonesHabilidad[i] = new Rectangle(startX, startY, iconWidth, iconHeight);
            }

        } catch (IOException e) {
            throw new RuntimeException("No pudo cargar el sprite de habilidades.", e);
        }
    }
}
