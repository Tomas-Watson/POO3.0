package Lanzador;

import java.awt.Graphics2D;

import com.entropyinteractive.JGame;

import JuegoLemmings.Lemmings;

public abstract class Juego extends JGame {


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

   /* @Override
public void run(double dt) {
    gameStartup();
    boolean running = true;

    // copia simplificada del bucle de JGame:
    while (running) {
        gameUpdate(dt);
        gameDraw(getGraphics2D());  // o como lo tengas implementado
        // … sleeping / sincronización de FPS

        // En algún momento de tu juego, marca running = false
        if (this instanceof Lemmings) {
            Lemmings lem = (Lemmings) this;
            if (lem.shouldStop) {   // deberás exponer un campo público o getter
                running = false;
            }
        }
    }

    gameShutdown();
}*/


}
