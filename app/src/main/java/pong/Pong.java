package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;

import Lanzador.Juego;
import util.Configuracion;
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
    private boolean enterPresionado = false;
    final double velocidad = 400;
    private Clip musica;
    private boolean victoriaSonada = false;



    public Pong(){
        //super("Pong", ANCHO_PANTALLA,ALTO_PANTALLA);
    }

    @Override
    public void gameStartup() {
        Log.info(getClass().getSimpleName(), "Ejecutando el juego");

        // Música de fondo
        if (Configuracion.get().musicaActivada) {
            musica = Sonido.reproducirMusica("musica/" + Configuracion.get().pistaMusical + ".wav");
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
           
            // Pausar/reanudar el juego con 'P'
            if (keyboard.isKeyPressed(KeyEvent.VK_P)) {
                if (!pPresionado) {
                    enPausa = !enPausa;
                    pPresionado = true;
                }
            } else {
                pPresionado = false;
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_Q)) {
                if (!qPresionado) {
                    Configuracion.get().sonidoActivado = !Configuracion.get().sonidoActivado;
                    System.out.println("Sonido: " + (Configuracion.get().sonidoActivado ? "ON" : "OFF"));
                    qPresionado = true;
                }
            } else {
                qPresionado = false;
            }

            // Toggle música
            if (keyboard.isKeyPressed(KeyEvent.VK_E)) {
                if (!ePresionado) {
                    Configuracion.get().musicaActivada = !Configuracion.get().musicaActivada;
                    System.out.println("Música: " + (Configuracion.get().musicaActivada ? "ON" : "OFF"));

                    if (!Configuracion.get().musicaActivada) {
                        Sonido.detenerMusica(musica);
                    } else {
                        musica = Sonido.reproducirMusica("musica/" + Configuracion.get().pistaMusical + ".wav");
                    }

                    ePresionado = true;
                }
            } else {
                ePresionado = false;
            }

            // Reiniciar el juego con 'Enter'
            if (keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
                if (!enterPresionado) {
                    reiniciarJuego();
                    enterPresionado = true;
                }
            } else {
                enterPresionado = false;
            }

            if (!enPausa) {
                // Movimiento paleta 1
                if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
                    p1.setY(p1.getY() - velocidad * delta);
                    //System.out.println("Se movio arriba ");
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
                    p1.setY(p1.getY() + velocidad * delta);
                    //System.out.println("Se movio abajo");
                }

                // Movimiento paleta 2 (opcional: puedes usar W/S para el jugador 2)
                if (keyboard.isKeyPressed(KeyEvent.VK_W)) {
                    p2.setY(p2.getY() - velocidad * delta);
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_S)) {
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
                    if (!victoriaSonada && Configuracion.get().sonidoActivado) {
                        Sonido.reproducirEfecto("musica/victoria.wav"); // asegúrate de tener ese archivo
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

}
