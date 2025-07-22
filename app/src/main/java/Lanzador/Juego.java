package Lanzador;

import java.awt.Graphics2D;

import com.entropyinteractive.JGame;

public abstract class Juego extends JGame {

    protected boolean shouldStop = false;

    public Juego() {
        super("Juegos POO", 800, 600);
    }

    @Override
    public void gameStartup() {

    }

    @Override
    public void gameUpdate(double d) {

    }

    @Override
    public void gameDraw(Graphics2D gd) {

    }

    @Override
    public void gameShutdown() {

    }

    public void detenerJuego() {
        shouldStop = true;
    }

    @Override
    public void run(double dt) {
        gameStartup();

        while (!shouldStop) {
            gameUpdate(dt); // update and draw
        }

        gameShutdown(); // se detiene la música automáticamente aquí
    }

}
