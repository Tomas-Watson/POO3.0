package Lanzador;

import java.awt.Graphics2D;

import com.entropyinteractive.JGame;

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

}
