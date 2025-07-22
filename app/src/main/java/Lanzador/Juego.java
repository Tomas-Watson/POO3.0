package Lanzador;

import java.awt.Graphics2D;

import com.entropyinteractive.JGame;

import JuegoLemmings.Lemmings;

public abstract class Juego extends JGame {

    public Juego() {
        super("Juego Lemmings", 800, 600);

        
    }

    @Override
    public void gameStartup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void gameUpdate(double d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void gameDraw(Graphics2D gd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void gameShutdown() {
        throw new UnsupportedOperationException("Not supported yet.");
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
