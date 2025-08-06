package Lanzador;

import java.awt.Graphics2D;

import com.entropyinteractive.JGame;

import util.Sonido;

public abstract class Juego extends JGame {

    protected Sonido sonido;
    
    public Juego(Sonido sonido) {
        super("Juegos POO", 800, 600);
        this.sonido = sonido;
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

    public Sonido getSonido() {
        return sonido;
    }


}
