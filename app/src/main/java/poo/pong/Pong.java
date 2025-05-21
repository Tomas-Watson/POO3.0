package poo.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.example.Fondo;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;

import poo.DetectorColisiones;

public class Pong extends JGame{
    
    private Fondo fondo;
    private Color p1Color = Color.RED;
    private Color p2Color = Color.BLUE;
    private Color pelotaColor = Color.WHITE;	
    private Pelota pelota;
    private Paleta p1;
    private Paleta p2;
    private Contador contador;
    private boolean enPausa = false;
    private boolean finJuego = false;
    private boolean pPresionado = false;
    private boolean enterPresionado = false;

    final double velocidad = 150.0;

    public Pong(String arg0, int arg1, int arg2) {
        super("Pong", 800, 600);
    }

    @Override
    public void gameStartup() {
        Log.info(getClass().getSimpleName(), "Ejecutando el juego");
      
        fondo = new Fondo("app/src/main/resources/imagenes/Fondo_black.png");
        p1 = new Paleta(p1Color, 50, 250);
        p2 = new Paleta(p2Color, 50,250);
        contador = new Contador(0,0);
        pelota = new Pelota(pelotaColor, 700, 100);
    }

    @Override
    public void gameDraw(Graphics2D g) {
        // dibujar el fondo
        fondo.display(g);
        //dibujar raquetas
        p1.draw(g);
        p2.draw(g);
        //dibujar pelota
        if(!finJuego){
            g.setColor(Color.WHITE);
        }
        

        //dibujar contador
        contador.dibujar(g, fondo);
        if (enPausa) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics metrics = g.getFontMetrics();
            int x = ((int) fondo.getWidth() - metrics.stringWidth("Pausa")) / 2;
            int y = (int) fondo.getHeight() / 2;
            g.drawString("Pausa", x, y);
        } else if (finJuego) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics metrics = g.getFontMetrics();
            String mensajeFin = contador.getJ1ptos() > contador.getJ2ptos() ? "Gana Jugador 1" : "Gana Jugador 2";
            int x = ((int) fondo.getWidth() - metrics.stringWidth(mensajeFin)) / 2;
            int y = (int) fondo.getHeight() / 2;
            g.drawString(mensajeFin, x, y);
            String reiniciarMensaje = "Presione Enter para reiniciar";
            x = ((int) fondo.getWidth() - metrics.stringWidth(reiniciarMensaje)) / 2;
            y += 50;
            g.drawString(reiniciarMensaje, x, y);
        }
    }
    

    @Override
    public void gameShutdown() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameShutdown'");
    }

    @Override
    public void gameUpdate(double delta) {
        if (!finJuego) {
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
                }
                if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
                    p1.setY(p1.getY() + velocidad * delta);
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
                pelota.moverse(delta);

                // Colisión pelota con bordes superior/inferior
                DetectorColisiones.colisionPelotaContraBordesSupInf(pelota, fondo);

                // Colisión pelota con paletas
                DetectorColisiones.colisionPelotaRaqueta(pelota, p1);
                DetectorColisiones.colisionPelotaRaqueta(pelota, p2);

                // Goles
                if (DetectorColisiones.colisionPelotaContraLateralIzquierda(pelota)) {
                    contador.sumarPuntos(2); // Punto para jugador 2
                    reiniciarPosiciones();
                }
                if (DetectorColisiones.colisionPelotaContraLateralDerecha(pelota, fondo.getWidth())) {
                    contador.sumarPuntos(1); // Punto para jugador 1
                    reiniciarPosiciones();
                }
            }
        }
    }

    // Método para reiniciar posiciones tras un gol
    private void reiniciarPosiciones() {
        pelota.setX((double) (fondo.getWidth() / 2));
        pelota.setY((double) (fondo.getHeight() / 2));
        p1.setY(fondo.getHeight() / 2 - p1.getAlto() / 2);
        p2.setY(fondo.getHeight() / 2 - p2.getAlto() / 2);
    }

    public void reiniciarJuego() {
        contador.setJ1ptos(0);
        contador.setJ2ptos(0);
        finJuego = false;
        enPausa = false;
    } 
    

    public void limitesPaletas(Paleta p1, Paleta p2) {
        final int PADDING_TOP = 32;
        final int PADDING_BOTTOM = 0;
        if (p1.getY() < PADDING_TOP) {
            p1.setY(PADDING_TOP);
        }
        if (p1.getY() + p1.getAlto() > fondo.getHeight() - PADDING_BOTTOM) {
            p1.setY(fondo.getHeight() - PADDING_BOTTOM - p1.getAlto());
        }
        if (p2.getY() < PADDING_TOP) {
            p2.setY(PADDING_TOP);
        }
        if (p2.getY() + p2.getAlto() > fondo.getHeight() - PADDING_BOTTOM) {
            p2.setY(fondo.getHeight() - PADDING_BOTTOM - p2.getAlto());
        }
    }
}
