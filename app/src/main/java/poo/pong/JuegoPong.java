package poo.pong;


import org.example.Fondo;
import org.example.ObjetoGraficoMovible;
import org.example.ObjetoGrafico;
import java.awt.event.*; 
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.RenderingHints.Key;
import java.io.*;

import poo.Videojuego;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;

public class JuegoPong extends JGame {

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

    public JuegoPong(String arg0, int arg1, int arg2) {
        super("Pong", 800, 600);
    }

    @Override
    public void gameStartup() {
        Log.info(getClass().getSimpleName(), "Ejecutando el juego");
        //fondo = new Fondo() adentro del Fondo hay que poner el nombre de la imagen
        fondo = new Fondo("app.src.main.resources.imagenes.background.png");
        p1 = new Paleta(p1Color, 50, 250);
        p2 = new Paleta(p2Color, 50,250);
        contador = new Contador(0,0);
        pelota = new Pelota(pelotaColor, 100, 100);
    }

    @Override
    public void gameDraw(Graphics2D g) {
        // dibujar el fondo
        fondo.display(g);
        //dibujar raquetas
        p1.display(g);
        p2.display(g);
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
                moverPelota(delta);

                // Colisiones
                colisionBordes();
                colisionPaletas();

                // Goles
                verificarGol();
            }
        }
    }

    // Mueve la pelota según su velocidad
    private void moverPelota(double delta) {
        pelota.moverse(delta);
    }

    // Rebota la pelota si toca el borde superior/inferior
    private void colisionBordes() {
        if (pelota.getY() <= 0 || pelota.getY() + pelota.getRadio() * 2 >= fondo.getHeight()) {
            pelota.rebotarVertical();
        }
    }

    // Rebota la pelota si choca con una paleta
    private void colisionPaletas() {
        // Paleta 1
        if (pelota.getX() <= p1.getX() + p1.getAncho() &&
            pelota.getY() + pelota.getRadio() * 2 >= p1.getY() &&
            pelota.getY() <= p1.getY() + p1.getAlto()) {
            pelota.rebotarHorizontal();
        }
        // Paleta 2
        if (pelota.getX() + pelota.getRadio() * 2 >= p2.getX() &&
            pelota.getY() + pelota.getRadio() * 2 >= p2.getY() &&
            pelota.getY() <= p2.getY() + p2.getAlto()) {
            pelota.rebotarHorizontal();
        }
    }

    // Detecta si hay gol y suma puntos
    private void verificarGol() {
        if (pelota.getX() < 0) {
            contador.sumarPuntos(2); // Gol para jugador 2
            reiniciarPosiciones();
        } else if (pelota.getX() + pelota.getRadio() * 2 > fondo.getWidth()) {
            contador.sumarPuntos(1); // Gol para jugador 1
            reiniciarPosiciones();
        }
    }

    // Reinicia posiciones de pelota y paletas tras un gol
    private void reiniciarPosiciones() {
        pelota.setX((double)fondo.getWidth() / 2);
        pelota.setX((double)fondo.getHeight() / 2);
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
