package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;

import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;

import Lanzador.Juego;
import util.Sonido;


public class Pong extends Juego{
    
    private final static int ANCHO_PANTALLA = 800;
    private final static int ALTO_PANTALLA = 600;	

    private Pelota pelota;
    private Paleta p1;
    private Paleta p2;

    private Contador contador;
    private boolean enPausa = false;
    private boolean finJuego = false;
    private boolean pPresionado = false;
    private boolean qPresionado = false;
    private boolean ePresionado = false;
    private boolean rPresionada = false;
    final double velocidad = 400;
    
    private Clip musica;
    private boolean victoriaSonada = false;

    private ConfiguracionPong config;
    private boolean ventanaConfiguracionAbierta = false;
    private ConfiguracionPongGUI ventanaConfigGUI;


    public Pong(){
        //super("Pong", ANCHO_PANTALLA,ALTO_PANTALLA);
    }

    @Override
    public void gameStartup() {
        Log.info(getClass().getSimpleName(), "Ejecutando el juego");
        ConfiguracionPong config = ConfiguracionPong.get();

        // Música de fondo
        if (config.isMusicaActivada()) {
            musica = Sonido.reproducirMusica("musica/" + config.getPistaMusical() + ".wav");
        }

        //Creo las paletas
        p1 = new Paleta( 780, 250);
        p2 = new Paleta(15,250);

        //Creo el contador
        contador = new Contador(0,0);

        //Creo la pelota 
        pelota = new Pelota(ANCHO_PANTALLA/2, ALTO_PANTALLA/2);
    }

    @Override
    public void gameDraw(Graphics2D g) {

        // dibujar el fondo
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, ANCHO_PANTALLA,ALTO_PANTALLA);
        
        //dibujar raquetas
        p1.display(g);
        p2.display(g);

        //dibujar pelota
        g.setColor(Color.BLUE);
        pelota.draw(g);

        //dibujar contador
        contador.dibujar(g);

        if (enPausa) {

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));

            FontMetrics metrics = g.getFontMetrics();
            int x = (ANCHO_PANTALLA - metrics.stringWidth("Pausa")) / 2;
            int y = ALTO_PANTALLA / 2;

            g.drawString("Pausa", x, y);

        } 

        if (contador.getGanador() != null) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Ganador: " + contador.getGanador(), 250, 200);
        }
    }
    

    @Override
    public void gameShutdown() {
        
        Sonido.detenerMusica(musica);
    }

    @Override
    public void gameUpdate(double delta) {
        Keyboard keyboard = this.getKeyboard();

        if (keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)){
            System.exit(0);
        }

        if (!finJuego) {
           
            // Pausar/reanudar el juego with 'P'
            if (keyboard.isKeyPressed(KeyEvent.VK_P)) {
                if (!pPresionado) {
                    enPausa = !enPausa;
                    pPresionado = true;
                }
            } else {
                pPresionado = false;
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_C)) {
                if (!ventanaConfiguracionAbierta) {
                    ventanaConfiguracionAbierta = true;
                    enPausa = true;


                    SwingUtilities.invokeLater(() -> {
                        ventanaConfigGUI = new ConfiguracionPongGUI(this); // Pasás referencia al juego
                    });
                }
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_Q)) {
                if (!qPresionado) {
                    config = ConfiguracionPong.get();
                    config.setSonidoActivado(!config.isSonidoActivado());
                    System.out.println("Sonido: " + (config.isSonidoActivado() ? "ON" : "OFF"));
                    qPresionado = true;
                }
            } else {
                qPresionado = false;
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_E)) {
                if (!ePresionado) {
                    config = ConfiguracionPong.get();
                    config.setMusicaActivada(!config.isMusicaActivada());
                    System.out.println("Música: " + (config.isMusicaActivada() ? "ON" : "OFF"));

                    if (!config.isMusicaActivada()) {
                        Sonido.detenerMusica(musica);
                    } else {
                        musica = Sonido.reproducirMusica("musica/" + config.getPistaMusical() + ".wav");
                    }

                    ePresionado = true;
                }
            } else {
                ePresionado = false;
            }


            if (keyboard.isKeyPressed(KeyEvent.VK_R)) {
                if (!rPresionada) {
                    reiniciarJuego();
                    reiniciarPosiciones();
                    rPresionada = true;
                }
            } else {
                rPresionada = false;
            }

            if (!enPausa) {
                // Mover las paletas con las teclas configuradas
                Map<String, Integer> teclas = ConfiguracionPong.get().getTeclas();
                int j1Up = teclas.get("J1_UP");
                int j1Down = teclas.get("J1_DOWN");
                int j2Up = teclas.get("J2_UP");
                int j2Down = teclas.get("J2_DOWN");

                if (keyboard.isKeyPressed(j1Up)) {
                    p1.setY(p1.getY() - velocidad * delta);
                }
                if (keyboard.isKeyPressed(j1Down)) {
                    p1.setY(p1.getY() + velocidad * delta);
                }
                if (keyboard.isKeyPressed(j2Up)) {
                    p2.setY(p2.getY() - velocidad * delta);
                }
                if (keyboard.isKeyPressed(j2Down)) {
                    p2.setY(p2.getY() + velocidad * delta);
                }

                // Limitar paletas dentro del área de juego
                limitesPaletas(p1, p2);

                // Mover la pelota
                pelota.moverse((delta * 25));

                // Colisión pelota con paletas
                DetectorColisiones.colisionPelotaRaqueta(pelota, p1);
                DetectorColisiones.colisionPelotaRaqueta(pelota, p2);

                // Goles
                if (DetectorColisiones.colisionPelotaContraLateralIzquierda(pelota)) {
                    contador.sumarPuntoJugador2(); // Punto para jugador 2
                    reiniciarPosiciones();
                }
                if (DetectorColisiones.colisionPelotaContraLateralDerecha(pelota,ANCHO_PANTALLA)) {
                    contador.sumarPuntoJugador1(); // Punto para jugador 1
                    reiniciarPosiciones();
                }

                if (contador.getGanador() != null) {
                    if (!victoriaSonada && ConfiguracionPong.get().isSonidoActivado()) {
                        Sonido.reproducirEfecto("musica/victoria.wav");
                        Sonido.detenerMusica(musica);
                        victoriaSonada = true;
                    }
                    finJuego = true;
                }

                

                DetectorColisiones.colisionPelotaContraBordesSupInf(pelota);
            }
        }
    }

    // Método para reiniciar posiciones tras un gol
    private void reiniciarPosiciones() {
        pelota.setX((double) (ANCHO_PANTALLA / 2));
        pelota.setY((double) (ANCHO_PANTALLA / 2));
        pelota.reiniciarVelocidad();
        p1.setY(ALTO_PANTALLA / 2 - p1.getAlto() / 2);
        p2.setY(ALTO_PANTALLA / 2 - p2.getAlto() / 2);
    }

    public void reiniciarJuego() {
        contador.setJ1ptos(0);
        contador.setJ2ptos(0);
        finJuego = false;
        enPausa = false;
        victoriaSonada = false;
    } 
    

    public void limitesPaletas(Paleta p1, Paleta p2) {
        final int PADDING_TOP = 32;
        final int PADDING_BOTTOM = 0;
        if (p1.getY() < PADDING_TOP) {
            p1.setY(PADDING_TOP);
        }
        if (p1.getY() + p1.getAlto() > ALTO_PANTALLA - PADDING_BOTTOM) {
            p1.setY(ALTO_PANTALLA - PADDING_BOTTOM - p1.getAlto());
        }
        if (p2.getY() < PADDING_TOP) {
            p2.setY(PADDING_TOP);
        }
        if (p2.getY() + p2.getAlto() > ALTO_PANTALLA - PADDING_BOTTOM) {
            p2.setY(ALTO_PANTALLA - PADDING_BOTTOM - p2.getAlto());
        }
    }

    public void configuracionCerrada() {
        ventanaConfiguracionAbierta = false;
        enPausa = false;
        ventanaConfigGUI = null; // libera la referencia
    }

}
